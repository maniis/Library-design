package com.example.library.library.dao.enums;

public enum ItemType {
    BOOK("Book"),
    DVD("DVD");

    private ItemType(final String item) {
        this.item = item;
    }
    
    private String item;
 
    public String getType() {
        return item;
    }
}
