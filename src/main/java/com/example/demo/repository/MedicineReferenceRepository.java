package com.example.demo.repository;

import com.example.demo.model.MedicineReference;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MedicineReferenceRepository extends MongoRepository<MedicineReference, String> {
    List<MedicineReference> findTop20ByNameIgnoreCaseContaining(String name);
}