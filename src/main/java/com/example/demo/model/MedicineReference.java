package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "medicine_reference")
public class    MedicineReference {

    @Id
    private String id;
    private String name;
    private String companyName;

    public String getStripCount() {
        return stripCount;
    }

    public void setStripCount(String stripCount) {
        this.stripCount = stripCount;
    }

    private String stripCount;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }


}
