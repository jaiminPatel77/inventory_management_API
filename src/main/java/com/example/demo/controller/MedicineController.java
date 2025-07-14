package com.example.demo.controller;

import com.example.demo.model.BilledMedicine;
import com.example.demo.model.MedicalBillHistory;
import com.example.demo.model.Medicine;
import com.example.demo.repository.MedicineRepository;
import com.example.demo.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medicines")
@CrossOrigin(origins = "*")
public class MedicineController {

    @Autowired
    private MedicineRepository repository;

    @Autowired
    private MedicineService medicineService;

    @GetMapping
    public ResponseEntity<List<Medicine>> getAllMedicines() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Medicine> createMedicine(@RequestBody Medicine medicine) {
        Medicine saved = repository.save(medicine);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
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

    @PostMapping("/bill")
    public ResponseEntity<String> processBill(@RequestBody List<BilledMedicine> billedItems) {
        for (BilledMedicine item : billedItems) {
            Optional<Medicine> medOpt = repository.findById(item.getId());
            if (medOpt.isPresent()) {
                Medicine med = medOpt.get();
                int remainingQty = med.getQuantity() - item.getPurchasedQuantity();
                if (remainingQty < 0) {
                    return ResponseEntity.badRequest().body("Not enough stock for: " + med.getName());
                }
                med.setQuantity(remainingQty);
                repository.save(med);
            }
        }
        return ResponseEntity.ok("Inventory updated successfully");
    }

    @PostMapping("/bills")
    public ResponseEntity<MedicalBillHistory> saveBill(@RequestBody MedicalBillHistory bill) {
        return ResponseEntity.ok(medicineService.saveBill(bill));
    }

    @GetMapping("/bills")
    public ResponseEntity<List<MedicalBillHistory>> getAllBills() {
        return ResponseEntity.ok(medicineService.getAllBills());
    }


}
