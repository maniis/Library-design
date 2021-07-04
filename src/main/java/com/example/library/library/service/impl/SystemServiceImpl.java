package com.example.library.library.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.library.library.constants.LibraryConstants.*;

import com.example.library.library.dao.entity.Customer;
import com.example.library.library.dao.entity.Item;
import com.example.library.library.dao.entity.Record;
import com.example.library.library.dao.enums.ItemType;
import com.example.library.library.dao.enums.ItemStatus;
import com.example.library.library.dao.repository.BookRepository;
import com.example.library.library.dao.repository.CustomerRepository;
import com.example.library.library.dao.repository.DVDRepository;
import com.example.library.library.dao.repository.RecordRepository;
import com.example.library.library.exception.LibraryCustomException;
import com.example.library.library.service.SystemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    DVDRepository dvdRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RecordRepository recordRepository;

    @Override
    public synchronized String addAccount(Customer newCustomer) {

        if(customerRepository.searchByContactNumber(newCustomer.getContactNo())==null){
            customerRepository.save(newCustomer);
            return ACCOUNT_CREATED +String.valueOf(newCustomer.getCustomerId());
        }else
            return ACCOUNT_EXISTS;  
    }


    @Override
    public Customer getCustomer(String customerId) {
        try{
            Customer customer= customerRepository.searchByCustomerId(Long.parseLong(customerId));
            if(customer!=null)
                return customer;
            else{
                throw new LibraryCustomException(INVALID_ACCOUNT);
            }
        }
        catch(Exception ex){
            throw new LibraryCustomException(INVALID_ACCOUNT);
        }
    }


    @Override
    public List<Item> searchItem(String itemType, String title) {
        List<Item> items = new ArrayList<>();
        try{
            if(itemType.equalsIgnoreCase(ItemType.BOOK.getType()))
                items= bookRepository.searchByTitle(title);
            else if(itemType.equalsIgnoreCase(ItemType.DVD.getType()))
                items= dvdRepository.searchByTitle(title);
        }catch(Exception ex){
            throw new LibraryCustomException(ITEM_NOT_AVAILABLE);
        }
        return items;
    }

    @Override
    public String  createNewRecord(String customerId, String itemType, String itemId) {
        Customer customer = getCustomer(customerId);
        Item item= getItem(itemType,itemId);
        if(item.getStatus().equals(ItemStatus.RENTED.getStatus()))
            return ITEM_NOT_AVAILABLE;
        else{
            try{
                return createRecord(customer, item);
            }catch(Exception ex){
                return UPDATE_FAILURE_MESSAGE;
            }
        }
    }

    @Override
    public String updateExistingRecord(String customerId, String itemType, String itemId, Double fine, Integer duration) {
        Customer customer = getCustomer(customerId);
        Item item =getItem(itemType, itemId);
        Record record = getRecord(customerId, itemId);
        modifyRecord(fine, duration, record);
        String message= updateItemStatus(item,ItemStatus.AVAILABLE.getStatus());
        if(customer!=null && message.equalsIgnoreCase(UPDATE_SUCCESS_MESSAGE)){
            recordRepository.save(record);
            return ITEM_RETURNED;
        }
        else
            return UPDATE_RECORD_FAILED;
    
    }


    private void modifyRecord(Double fine, Integer duration, Record record) {
        record.setFine(fine);
        record.setDuration(duration);
        record.setReturnDate(new Date());
    }


    @Override
    public Record getRecord(String customerId, String itemId) {
        try{
            Customer customer = getCustomer(customerId);
            Record record= recordRepository.searchActiveRecord(Long.parseLong(customerId),itemId);
            if(customer!=null && record!=null)
                return record;
            else{
                throw new LibraryCustomException(INVALID_ITEM_DETAILS);
            }
        }catch(Exception ex){
            throw new LibraryCustomException(ex.getMessage());
        }
    }


    @Override
    public List<Record> getRecordsList(String customerId) {

        try{
           Customer customer = getCustomer(customerId);
           List<Record> records= recordRepository.searchAllRecord(Long.parseLong(customerId));  
           if(customer!=null && records!=null && records.size()>0)
                return records;
            else{
                throw new LibraryCustomException(INVALID_ITEM_DETAILS);
            }
        }catch(Exception ex){
            throw new LibraryCustomException(ex.getMessage());
        }
    }


    public Item getItem(String itemType, String itemId) {
        Item item = null;
        try{
            if(itemType.equalsIgnoreCase(ItemType.BOOK.getType()))
                item= bookRepository.searchByItemId(itemId);
            else if(itemType.equalsIgnoreCase(ItemType.DVD.getType()))
                item= dvdRepository.searchByItemId(itemId);
        }
        catch(Exception ex){
            throw new LibraryCustomException(INVALID_ITEM_DETAILS);  
        }
        return item;
     }

     public String createRecord(Customer customer, Item item) {

        String message=updateItemStatus(item,ItemStatus.RENTED.getStatus());
        if(message.equalsIgnoreCase(UPDATE_FAILURE_MESSAGE))
            return ITEM_NOT_AVAILABLE; 
        
        Record record = new Record.RecordBuilder(item.getItemId(),item.getItemType(),item.getItemName())
                            .setFine(0.0).setCustomerId(customer.getCustomerId()).setIssueDate(new Date())
                            .setDuration(DEFAULLT_TOTAL_DURATION).build();
        recordRepository.save(record);
        return ITEM_ISSUED;    
    }



    public synchronized String updateItemStatus(Item item, String status) {

        Item currentItem = getItem(item.getItemType(), item.getItemId());
        
        if(!currentItem.getStatus().equalsIgnoreCase(item.getStatus()))
            return UPDATE_FAILURE_MESSAGE;

        if(item.getItemType().equalsIgnoreCase(ItemType.BOOK.getType()))
            bookRepository.updateItemStatus(item.getItemId(),status);
        else if(item.getItemType().equalsIgnoreCase(ItemType.DVD.getType()))
            dvdRepository.updateItemStatus(item.getItemId(),status);
        return UPDATE_SUCCESS_MESSAGE;
        
    }



}
