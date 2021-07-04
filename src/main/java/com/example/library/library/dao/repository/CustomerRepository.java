package com.example.library.library.dao.repository;


import com.example.library.library.dao.entity.Customer;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Long> {


    @Query(value = "select c from Customer c where c.customerId = :customerId")
    Customer searchByCustomerId(@Param("customerId")long customerId);

    @Query(value = "select c from Customer c where c.contactNo = :contactNo")
    Customer searchByContactNumber(@Param("contactNo")String contactNo);

    @Modifying(clearAutomatically = true)
    @Query(value = "insert into Customers (name,address,contactNo) "
            + "VALUES (:#{#customer.name}, :#{#customer.address}, :#{#customer.contactNo} )", nativeQuery = true)
    void addCustomer(@Param("customer") Customer customer);


   
   
    

    
}
