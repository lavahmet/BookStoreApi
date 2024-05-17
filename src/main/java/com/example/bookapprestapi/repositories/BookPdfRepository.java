package com.example.bookapprestapi.repositories;

import com.example.bookapprestapi.models.BookPDF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookPdfRepository extends JpaRepository<BookPDF, Integer> {
    BookPDF findByBook_BookId(int id);
}
