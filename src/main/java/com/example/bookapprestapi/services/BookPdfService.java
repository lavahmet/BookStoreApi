package com.example.bookapprestapi.services;

import com.example.bookapprestapi.models.BookPDF;
import com.example.bookapprestapi.repositories.BookPdfRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookPdfService {
    private final BookPdfRepository bookPdfRepository;
    private final BookService bookService;

    public BookPDF findById(int id) {
        return bookPdfRepository.findByBook_BookId(id);
    }

    public void save(byte[] pdfContent, int bookId) {
        if (bookPdfRepository.findByBook_BookId(bookId) == null) {
            BookPDF bookPDF = new BookPDF();
            bookPDF.setBook(bookService.findById(bookId));
            bookPDF.setContent(pdfContent);
            bookPdfRepository.save(bookPDF);
        }
    }

    public boolean checkBookId(int bookId) {
        if (bookPdfRepository.findByBook_BookId(bookId) == null) {
            return false;
        }
        return bookPdfRepository.existsById(bookPdfRepository.findByBook_BookId(bookId).getPdfId());
    }

    public void editPdf(int id, byte[] pdfContent) {
        BookPDF existingPdf = bookPdfRepository.findByBook_BookId(id);
        if (existingPdf != null) {
            existingPdf.setPdfId(existingPdf.getPdfId());
            existingPdf.setContent(pdfContent);
            bookPdfRepository.save(existingPdf);
        }
    }

    public void deleteById(int id) {
        bookPdfRepository.deleteById(findById(id).getPdfId());
    }
}
