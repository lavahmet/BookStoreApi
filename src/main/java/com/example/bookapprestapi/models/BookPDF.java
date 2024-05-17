package com.example.bookapprestapi.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "book_pdf")
@Data
public class BookPDF {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_pdf_id")
    private int pdfId;

    @OneToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "pdf_content", columnDefinition = "bytea")
    private byte[] content;

}
