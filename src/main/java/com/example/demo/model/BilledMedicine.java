package com.example.demo.model;

public class BilledMedicine {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPurchasedQuantity() {
        return purchasedQuantity;
    }

    public void setPurchasedQuantity(int purchasedQuantity) {
        this.purchasedQuantity = purchasedQuantity;
    }

    private String id;
    private int purchasedQuantity;
}
