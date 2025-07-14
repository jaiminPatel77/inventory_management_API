package com.example.demo.service;

import com.example.demo.model.MedicalBillHistory;
import com.example.demo.repository.MedicalBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MedicineService {

    @Autowired
    private MedicalBillRepository medicalBillRepository;

    public MedicalBillHistory saveBill(MedicalBillHistory bill) {
        return medicalBillRepository.save(bill);
    }

    public List<MedicalBillHistory> getAllBills() {
        return medicalBillRepository.findAll();
    }
}
