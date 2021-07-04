package com.example.library.library;

import com.example.library.library.service.LibrarianService;
import com.example.library.library.service.SystemService;
import com.example.library.library.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootConsoleApplication 
  implements CommandLineRunner {

    private static Logger logger = LoggerFactory
      .getLogger(SpringBootConsoleApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootConsoleApplication.class, args);
    }

    // @Value("${input.file}")
    // String file;

    @Autowired
    SystemService systemService;

    @Autowired
    UserService userService;

    @Autowired
    LibrarianService librarianService;

    
    @Override
    public void run(String... args) 
    {
      logger.info("EXECUTING : command line runner");
      try{
          userService.createAccount("DummyUser1", "City-xyz","2233244");
          userService.createAccount("DummyUser2", "City-xyz","121732");
          userService.createAccount("DummyUser3", "City-xyz","1217322");
          userService.createAccount("DummyUser4", "City-xyz","1217322");
          userService.searchItem("100001","Book","Fundamentals of Wavelets");
          userService.searchItem("100002","DVD","Fundamentals of Wavelets");
          userService.borrowItem("100001", "Book", "1001");
          userService.borrowItem("100002", "DVD", "1002");
          userService.searchItem("100003","DVD","Encylopedia");
          userService.borrowItem("100002", "Book", "1001");
          userService.borrowItem("100003", "DVD", "1001");
          userService.borrowItem("100001", "DVD", "1001");
          userService.borrowItem("100002", "DVD", "1001");
          userService.returnItem("100001", "Book", "1001");
          userService.returnItem("100002", "DVD", "1002");
          userService.returnItem("100003","DVD","1004");
          userService.returnItem("100002", "Book", "1001");
          userService.returnItem("100003", "DVD", "1001");
          userService.returnItem("100001", "DVD", "1001");
          userService.returnItem("100002", "DVD", "1001");
          userService.borrowItem("100002", "Book", "1001");
          userService.borrowItem("100003", "DVD", "1001");
          userService.borrowItem("100001", "DVD", "1001");
          userService.borrowItem("100002", "DVD", "1001");
          librarianService.getCustomerInfo("100001");
          librarianService.calculateFineForCustomer("100001");
          librarianService.getOverdueitemsListForCustomer("100001");
          librarianService.getRentedSoFarListForCustomer("100001");
          librarianService.getCustomerInfo("100002");
          librarianService.calculateFineForCustomer("100002");
          librarianService.getOverdueitemsListForCustomer("100002");
          librarianService.getRentedSoFarListForCustomer("100002");
          librarianService.getCustomerInfo("100003");
          librarianService.calculateFineForCustomer("100003");
          librarianService.getOverdueitemsListForCustomer("100003");
          librarianService.getRentedSoFarListForCustomer("100003");

          //invalid queries
          userService.searchItem("10000234","DVD","Fundamentals of Wavelets");
          userService.borrowItem("10000234", "DVD", "1001");
          userService.borrowItem("100002", "DVD", "1004441");
          userService.returnItem("10000234", "DVD", "1001");
          userService.returnItem("100001", "DVD", "100551");
          librarianService.getCustomerInfo("100002");
          librarianService.calculateFineForCustomer("10000234");
          librarianService.getOverdueitemsListForCustomer("10000234");
          librarianService.getRentedSoFarListForCustomer("10000234");

      }catch(Exception ex){
        logger.error("Error ocuured {}", ex.getMessage());
      }
 
    }
}
