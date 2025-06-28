package com.example.demo.controller;

import com.example.demo.model.MedicineReference;
import com.example.demo.repository.MedicineReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/reference-upload")
@CrossOrigin
public class MedicineReferenceUploadController {

    @Autowired
    private MedicineReferenceRepository referenceRepository;

    @GetMapping
    public List<MedicineReference> getAllReferences() {
        return referenceRepository.findAll();
    }
    @GetMapping("/search")
    public List<MedicineReference> searchByName(@RequestParam("query") String query) {
        System.out.println("Searching for: " + query);
        return referenceRepository.findTop20ByNameIgnoreCaseContaining(query);
    }

    @PostMapping("/csv")
    public String uploadReferenceCSV(@RequestParam("file") MultipartFile file) {
        int batchSize = 1000;
        List<MedicineReference> batch = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line = reader.readLine(); // Skip header
            int totalCount = 0;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                MedicineReference med = new MedicineReference();
                med.setId(parts[0].trim());
                med.setName(parts[1].trim());
                med.setCompanyName(parts[2].trim());
                med.setStripCount(parts[4].trim());

                batch.add(med);

                if (batch.size() == batchSize) {
                    referenceRepository.saveAll(batch);
                    totalCount += batch.size();
                    batch.clear(); // Reset for next batch
                }
            }

            // Save any remaining items
            if (!batch.isEmpty()) {
                referenceRepository.saveAll(batch);
                totalCount += batch.size();
            }

            return "Uploaded " + totalCount + " reference medicines.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to upload file: " + e.getMessage();
        }
    }
}