package com.example.library.library.service;

public interface LibrarianService {

    String createNewAccount(String name, String address, String contact);
    String doBorrowTransaction(String customerId, String itemType, String itemId);
    String doReturnTransaction(String customerId, String itemType, String itemId);

    void getCustomerInfo(String CustomerId);
    void calculateFineForCustomer(String CustomerId);

    void getOverdueitemsListForCustomer(String CustomerId);
    void getRentedSoFarListForCustomer(String CustomerId);


    
}
