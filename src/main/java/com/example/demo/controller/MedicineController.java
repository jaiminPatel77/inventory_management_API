package com.example.demo.controller;

import com.example.demo.model.Medicine;
import com.example.demo.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
@CrossOrigin(origins = "*")
public class MedicineController {

    @Autowired
    private MedicineRepository repository;

    @GetMapping
    public List<Medicine> getAllMedicines() {
        return repository.findAll();
    }

    @PostMapping
    public Medicine createMedicine(@RequestBody Medicine medicine) {
        return repository.save(medicine);
    }

    @DeleteMapping("/{id}")
    public void deleteMedicine(@PathVariable String id) {
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Medicine updateMedicine(@PathVariable String id, @RequestBody Medicine updatedMedicine) {
        return repository.findById(id).map(med -> {
            med.setName(updatedMedicine.getName());
            med.setPack(updatedMedicine.getPack());
            med.setBatchNo(updatedMedicine.getBatchNo());
            med.setQuantity(updatedMedicine.getQuantity());
            med.setPrice(updatedMedicine.getPrice());
            med.setExpiryDate(updatedMedicine.getExpiryDate());
            return repository.save(med);
        }).orElseGet(() -> {
            // If not found, save it as new with given ID
            updatedMedicine.setId(id);
            return repository.save(updatedMedicine);
        });
    }

}
