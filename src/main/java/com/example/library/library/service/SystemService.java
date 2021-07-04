package com.example.library.library.service;

import java.util.List;
import com.example.library.library.dao.entity.Customer;
import com.example.library.library.dao.entity.Item;
import com.example.library.library.dao.entity.Record;

public interface SystemService {

    String addAccount(Customer customer);
    Customer getCustomer(String customerId);

    List<Item> searchItem(String itemType, String title);

    String createNewRecord(String customerId, String itemType, String itemId);
    String updateExistingRecord(String customerId, String itemType, String itemId, Double fine, Integer duration);
    Record getRecord(String customerId, String itemId);
    List<Record> getRecordsList(String customerId);
    
}
