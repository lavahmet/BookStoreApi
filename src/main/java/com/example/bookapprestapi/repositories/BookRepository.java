package com.example.bookapprestapi.repositories;

import com.example.bookapprestapi.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByCategory_CategoryId(int categoryId);
    List<Book> findByOrderByViewsCountDesc();
    List<Book> findByOrderByDownloadsCountDesc();
    List<Book> findByOrderByPriceAsc();
    List<Book> findByOrderByPriceDesc();


}
