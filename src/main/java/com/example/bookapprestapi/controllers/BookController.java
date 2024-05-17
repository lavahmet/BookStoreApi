package com.example.bookapprestapi.controllers;

import com.example.bookapprestapi.RequestDTO.RequestBookDTO;
import com.example.bookapprestapi.RequestDTO.RequestCommentDTO;
import com.example.bookapprestapi.ResponseDTO.ResponseBookDTO;
import com.example.bookapprestapi.ResponseDTO.ResponseCommentDTO;
import com.example.bookapprestapi.models.Book;
import com.example.bookapprestapi.models.Category;
import com.example.bookapprestapi.models.Comment;
import com.example.bookapprestapi.services.BookService;
import com.example.bookapprestapi.services.CategoryService;
import com.example.bookapprestapi.services.CommentService;
import com.example.bookapprestapi.util.exceptions.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;
    private final CommentService commentService;

    @GetMapping("/all")
    public List<ResponseBookDTO> getAllBook() {
        return bookService.findAll().stream()
                .map(this::convertModelToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseBookDTO getBookById(@PathVariable int id) {
        return convertModelToDTO(bookService.findById(id));
    }

    @GetMapping("/by/category/{categoryId}")
    public List<ResponseBookDTO> getBookByCategory(@PathVariable int categoryId) {
        return bookService.findByCategoryId(categoryId).stream()
                .map(this::convertModelToDTO).collect(Collectors.toList());
    }

    @GetMapping("/by/views")
    public List<ResponseBookDTO> getBookByViewsCount() {
        return bookService.findByViewsCount().stream()
                .map(this::convertModelToDTO).collect(Collectors.toList());
    }

    @GetMapping("/by/downloads")
    public List<ResponseBookDTO> getBookByDownloadsCount() {
        return bookService.findByDownloadsCount().stream()
                .map(this::convertModelToDTO).collect(Collectors.toList());
    }

    @GetMapping("/cheapest")
    public List<ResponseBookDTO> getBookByCheapestPrice() {
        return bookService.findByCheapest().stream()
                .map(this::convertModelToDTO).collect(Collectors.toList());
    }

    @GetMapping("/expensive")
    public List<ResponseBookDTO> getBookByExpensivePrice() {
        return bookService.findByExpensive().stream()
                .map(this::convertModelToDTO).collect(Collectors.toList());
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addBook(@RequestBody @Valid RequestBookDTO requestBookDTO,
                                              BindingResult bindingResult) {
        checkBindingResult(bindingResult);
        bookService.addBook(convertDTOtoModel(requestBookDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/add/comment/{id}")
    public ResponseEntity<HttpStatus> addCommentToBook(@PathVariable int id, @RequestBody @Valid RequestCommentDTO requestCommentDTO,
                                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errMsg = new StringBuilder();

            List<FieldError> errorList = bindingResult.getFieldErrors();
            for (FieldError fe : errorList) {
                errMsg.append(fe.getDefaultMessage()).append(";");
            }

            throw new CommentNotAdded(errMsg.toString());
        }
        commentService.addComment(convertCommentDTOtoModel(id, requestCommentDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/edit/{id}")
    public ResponseBookDTO editBook(@PathVariable int id, @RequestBody @Valid RequestBookDTO requestBookDTO,
                                    BindingResult bindingResult) {
        checkBindingResult(bindingResult);
        return convertModelToDTO(bookService.editBook(id, convertDTOtoModel(requestBookDTO)));
    }

    private void checkBindingResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errMsg = new StringBuilder();

            List<FieldError> errorList = bindingResult.getFieldErrors();
            for (FieldError fe : errorList) {
                errMsg.append(fe.getField())
                        .append(" - ").append(fe.getDefaultMessage())
                        .append(";");
            }

            throw new BookNotAddedException(errMsg.toString());
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
    }

    @ExceptionHandler
    private ResponseEntity<AppErrorResponse> handleException(BookNotFoundException e) {
        AppErrorResponse appErrorResponse = new AppErrorResponse(
                "Книга с таким id не найдена" + "\n" + e
        );
        return new ResponseEntity<>(appErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<AppErrorResponse> handleException(BookNotAddedException e) {
        AppErrorResponse appErrorResponse = new AppErrorResponse(e.getMessage());
        return new ResponseEntity<>(appErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<AppErrorResponse> handleException(CommentNotAdded e) {
        AppErrorResponse appErrorResponse = new AppErrorResponse(e.getMessage());
        return new ResponseEntity<>(appErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<AppErrorResponse> handleException(CategoryNotFoundException e) {
        AppErrorResponse appErrorResponse = new AppErrorResponse(e.getMessage());
        return new ResponseEntity<>(appErrorResponse, HttpStatus.NOT_FOUND);
    }

    private Book convertDTOtoModel(RequestBookDTO requestBookDTO) {
        Book book = modelMapper.map(requestBookDTO, Book.class);
        Category category = categoryService.findByCategoryName(requestBookDTO.getCategoryName());
        if (category == null) {
            throw new CategoryNotFoundException("Каткгоря не найдена");
        }
        book.setCategory(category);
        return book;
    }

    private ResponseBookDTO convertModelToDTO(Book book) {
        ResponseBookDTO responseBookDTO = modelMapper.map(book, ResponseBookDTO.class);
        responseBookDTO.setCategoryName(book.getCategoryName());
        responseBookDTO.setResponseCommentDTO(commentService.findByBookId(book.getBookId()).stream().map(this::convertCommentModelToDTO).collect(Collectors.toList()));
        return responseBookDTO;
    }

    private ResponseCommentDTO convertCommentModelToDTO(Comment comment) {
        ResponseCommentDTO responseCommentDTO = modelMapper.map(comment, ResponseCommentDTO.class);
        responseCommentDTO.setUsername(comment.getReader().getUsername());
        return responseCommentDTO;
    }

    private Comment convertCommentDTOtoModel(int id, RequestCommentDTO requestCommentDTO) {
        Comment comment = modelMapper.map(requestCommentDTO, Comment.class);
        Book book = bookService.findById(id);
        comment.setBook(book);
        return comment;
    }

}
