package com.example.library.library.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static com.example.library.library.constants.LibraryConstants.*;

import com.example.library.library.dao.entity.Customer;
import com.example.library.library.dao.entity.Record;
import com.example.library.library.exception.LibraryCustomException;
import com.example.library.library.service.LibrarianService;
import com.example.library.library.service.SystemService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

@Service
public class LibraryServiceImpl implements LibrarianService{

    private static final Logger logger = LoggerFactory.getLogger(LibraryServiceImpl.class);

    @Autowired
    SystemService systemService;

    @Override
    public String createNewAccount(String name, String address, String contact) {
        return systemService.addAccount(new Customer(name,address,contact));
    }

    
    @Override
    public String doBorrowTransaction(String customerId, String itemType, String itemId) {
        return systemService.createNewRecord(customerId, itemType,itemId);
    }

    @Override
    public String doReturnTransaction(String customerId, String itemType, String itemId) {
        StringBuilder sb = new StringBuilder();
        Record record = systemService.getRecord(customerId, itemId);
        int duration = calculateDuration(record);
        Double fine = calculateFine(duration);
        sb.append(FINE_CALCULATION_COMMAND).append(customerId).append(":").append(String.valueOf(fine)).append(" ");
        String message = systemService.updateExistingRecord(customerId, itemType,itemId,fine,duration);
        sb.append(message);
        return sb.toString();
    }
    
    @Async("taskExecutor")
    @Override
    public void calculateFineForCustomer(String customerId) {
        try{
            Future<List < Record> > records = new AsyncResult<List < Record>>(systemService.getRecordsList(customerId));
            List <Record> activeRecords = records.get().stream().filter(r->r.getReturnDate()==null).collect(Collectors.toList());
            double fine =0.0;
            for(Record record: activeRecords){
                int duration = calculateDuration(record);
                fine+=calculateFine(duration);
            }
            logger.info(printMessage(FINE_CALCULATION_COMMAND,customerId,String.valueOf(fine)));
        }catch (LibraryCustomException | InterruptedException | ExecutionException e) {
            logger.info(printMessage(FINE_CALCULATION_COMMAND,customerId,e.getMessage()));
        };
    }

    @Async("taskExecutor")
    @Override
    public void getCustomerInfo(String customerId) {
        try{
            Future<Customer> customer = new AsyncResult<Customer>(systemService.getCustomer(customerId));
            logger.info(printMessage(ACCOUNT_INFORMATION_COMMAND,customerId,customer.get().toString()));
        }catch (LibraryCustomException | InterruptedException | ExecutionException e) {
            logger.info(printMessage(ACCOUNT_INFORMATION_COMMAND,customerId,e.getMessage()));
        }
    }

    @Async("taskExecutor")
    @Override
    public void getOverdueitemsListForCustomer(String customerId) {
        try{
            List < Record> records = systemService.getRecordsList(customerId);
            List <Record> activeRecords = records.stream().filter(r->r.getReturnDate()==null).collect(Collectors.toList());
            logger.info(printMessage(DUE_SO_FAR_COMMAND,customerId, activeRecords));
        }catch (LibraryCustomException e) {
            logger.info(printMessage(DUE_SO_FAR_COMMAND,customerId,e.getMessage()));
        }

    }

    @Async("taskExecutor")
    @Override
    public void getRentedSoFarListForCustomer(String customerId) {
        try{
            List <Record> records = systemService.getRecordsList(customerId);
            logger.info(printMessage(RENTED_SO_FAR_COMMAND,customerId, records));  
        }catch (LibraryCustomException e) {
            logger.info(printMessage(RENTED_SO_FAR_COMMAND,customerId,e.getMessage()));
        } 
    }
    
    private int calculateDuration(Record record) {
        Date todaysDate = new Date();
        long diff = todaysDate.getTime() - record.getIssueDate().getTime();
        return (int) (diff / (24 * 60 * 60 * 1000));   
    }


    private Double calculateFine(int duration) {
        if(duration>DEFAULLT_TOTAL_DURATION){
            return (duration-DEFAULLT_TOTAL_DURATION)*FINE_VALUE_PER_DAY;
        }
        return 0.0;
    }

    private String printMessage(String command,String customerId,String message) {
        StringBuilder sb = new StringBuilder();
        sb.append(command).append(customerId).append(" ").append(message);
    
        return sb.toString();
    }

    private String printMessage(String command,String customerId, List<Record> list) {
        StringBuilder sb = new StringBuilder();
        sb.append(command).append(customerId).append(". Record size ").append(String.valueOf(list.size())).append("\n");
        for(Record record: list){
            sb.append(record.toString());
        }
        return sb.toString();
    }



}
