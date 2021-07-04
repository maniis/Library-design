package com.example.library.library.dao.repository;

import java.util.List;

import com.example.library.library.dao.entity.DVD;
import com.example.library.library.dao.entity.Item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DVDRepository extends JpaRepository<DVD, Long> {

    @Query(value = "select b from DVD b where b.title = :title and b.status='Available'")
    List<Item> searchByTitle(@Param("title") String title);

    @Query(value = "select b from DVD b where b.dvdId = :dvdId")
    Item searchByItemId(String dvdId);

    @Modifying
    @Query(value="Update DVD set status =:status where dvdId = :dvdId")
    void updateItemStatus( @Param("dvdId") String dvdId,@Param("status") String status );
    
}
