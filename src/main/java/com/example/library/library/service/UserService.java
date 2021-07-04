package com.example.library.library.service;


public interface UserService {

    public void createAccount(String name, String address, String contact );

    public void searchItem(String customerId, String itemType, String itemName);

    public void borrowItem(String customerId,String itemType, String itemId);

    public void returnItem(String customerId,String itemType, String itemId);
        
}
