package com.example.library.library.dao.enums;

public enum ItemStatus {
    AVAILABLE("Available"),
    RENTED("Rented");

    private ItemStatus(final String status) {
        this.status = status;
    }

    private String status;
 
    public String getStatus() {
        return status;
    }
}
