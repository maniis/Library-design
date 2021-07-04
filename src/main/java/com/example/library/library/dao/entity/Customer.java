package com.example.library.library.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="customers")
@SequenceGenerator(name="customer_id_seq", initialValue=100001)
public class Customer{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="customer_id_seq")
    private long customerId;

    private String name;

    private String address;

    @Column(name="contactNo",unique = true)
    private String contactNo;

    public Customer() {
    }

    public Customer(String name, String address, String contactNo) {
        this.name = name;
        this.address = address;
        this.contactNo = contactNo;
    }

    public long getCustomerId() {
        return customerId;
    }


    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public String getContactNo() {
        return contactNo;
    }


    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    @Override
    public String toString() {
        return "Customer [address=" + address + ", contactNo=" + contactNo + ", customerId=" + customerId + ", name="
                + name + "]";
    }

    
    
}
