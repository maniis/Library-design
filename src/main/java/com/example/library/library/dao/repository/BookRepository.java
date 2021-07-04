package com.example.library.library.dao.repository;

import java.util.List;

import com.example.library.library.dao.entity.Book;
import com.example.library.library.dao.entity.Item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "select b from Book b where b.title = :title and b.status='Available'")
    List<Item> searchByTitle(@Param("title") String title);

    @Query(value = "select b from Book b where b.bookId = :bookId")
    Item searchByItemId(String bookId);

    @Modifying
    @Query(value="Update Book set status =:status where bookId = :bookId")
    void updateItemStatus( @Param("bookId") String bookId,@Param("status") String status );


    
}
