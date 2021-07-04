package com.example.library.library.dao.repository;

import java.util.List;

import com.example.library.library.dao.entity.Record;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RecordRepository extends JpaRepository<Record, Long> {

    @Query(value = "select r from Record r where r.customerId = :customerId and r.itemId =:itemId")
    Record searchRecord(@Param("customerId") long customerId, @Param("itemId") String itemId);

    @Query(value = "select r from Record r where r.customerId = :customerId and r.itemId =:itemId and r.returnDate is null")
    Record searchActiveRecord(@Param("customerId") long customerId, @Param("itemId") String itemId);

    @Query(value = "select r from Record r where r.customerId = :customerId")
    List<Record> searchAllRecord(@Param("customerId") long customerId);
    
}
