package com.example.bookapprestapi.services;

import com.example.bookapprestapi.models.Book;
import com.example.bookapprestapi.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(int id) {
        Optional<Book> foundedBook = bookRepository.findById(id);
        return foundedBook.orElse(null);
    }

    public List<Book> findByCategoryId(int categoryId) {
        return bookRepository.findByCategory_CategoryId(categoryId);
    }

    public List<Book> findByViewsCount() {
        return bookRepository.findByOrderByViewsCountDesc();
    }

    public List<Book> findByDownloadsCount() {
        return bookRepository.findByOrderByDownloadsCountDesc();
    }

    public List<Book> findByCheapest() {
        return bookRepository.findByOrderByPriceAsc();
    }

    public List<Book> findByExpensive() {
        return bookRepository.findByOrderByPriceDesc();
    }

    @Transactional
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public Book editBook(int id, Book book) {
        book.setBookId(id);
        bookRepository.save(book);
        return findById(id);
    }

    @Transactional
    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }

}
