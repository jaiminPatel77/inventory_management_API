package com.example.demo.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "medical_bills")
public class MedicalBillHistory {

    @Id
    private String id;
    private String patientName;
    private String doctorName;
    private String billDate;
    private List<Medicine> items;
    private double totalAmount;
    private String htmlContent;
}

