package com.example.library.library.service.impl;


import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.example.library.library.constants.LibraryConstants.*;

import com.example.library.library.dao.entity.Item;
import com.example.library.library.exception.LibraryCustomException;
import com.example.library.library.service.LibrarianService;
import com.example.library.library.service.SystemService;
import com.example.library.library.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    SystemService systemService;

    @Autowired
    LibrarianService librarianService;

    @Async("taskExecutor")
    @Override
    public void createAccount(String name, String address, String contact) {
        try {
            logger.info("Requesting new user account creation request");
            Future<String> message=new AsyncResult<String> (librarianService.createNewAccount(name, address, contact));
            logger.info(message.get());
        }catch (LibraryCustomException | InterruptedException | ExecutionException | NullPointerException e) {
            logger.info(ACCOUNT_CREATION_FAILED);
        }
    }

    @Async("taskExecutor")
    @Override
    public void searchItem(String customerId, String itemType, String title) {
        try {
            Future<List<Item>> items= new AsyncResult<List<Item>> (systemService.searchItem(itemType,title)); 
            logger.info(printMessage(customerId,items.get()));
        }catch (LibraryCustomException | InterruptedException | ExecutionException | NullPointerException e) {
            logger.info(printMessage(customerId,e.getMessage()));
        }
    }

    @Async("taskExecutor")
    @Override
    public void borrowItem(String customerId, String itemType, String itemId) {
        try {
            Future<String> message=new AsyncResult<String> (librarianService.doBorrowTransaction(customerId, itemType, itemId));        
            logger.info(printMessage(customerId,message.get()));
        }catch (LibraryCustomException | InterruptedException | ExecutionException | NullPointerException e) {
            logger.info(printMessage(customerId,e.getMessage()));
        }
    }

    @Async("taskExecutor")
    @Override
    public void returnItem(String customerId, String itemType, String itemId) {
        try {
            Future<String> message=new AsyncResult<String> (librarianService.doReturnTransaction(customerId, itemType, itemId));        
            logger.info(printMessage(customerId,message.get()));
        }catch (LibraryCustomException | InterruptedException | ExecutionException | NullPointerException e) {
            logger.info(printMessage(customerId,e.getMessage()));
        }
    }


    private String printMessage(String customerId, List<Item> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("CustomerId: ").append(customerId).append(" Search Result ");
        if(list.size()==0)
            sb.append(ITEM_NOT_AVAILABLE);
        else{
            for(Item item: list){
                sb.append(item.toString());
            }
        }
        return sb.toString();
    }

    private String printMessage(String customerId,String message) {
        StringBuilder sb = new StringBuilder();
        sb.append("CustomerId: ").append(customerId).append(" Message: ").append(message);
        return sb.toString();
    }



 

    
}
