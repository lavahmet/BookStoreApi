package com.example.bookapprestapi.controllers;

import com.example.bookapprestapi.services.BookPdfService;
import com.example.bookapprestapi.util.exceptions.AppErrorResponse;
import com.example.bookapprestapi.util.exceptions.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/pdf")
@RequiredArgsConstructor
public class BookPdfController {

    private final BookPdfService pdfService;

    @GetMapping("/{bookId}")
    public ResponseEntity<?> getById(@PathVariable int bookId) {
        byte[] pdfContent = pdfService.findById(bookId).getContent();
        if (pdfContent != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfContent);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add/{bookId}")
    public ResponseEntity<?> addPdf(@PathVariable int bookId,
                                    @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Выберите файл!", HttpStatus.BAD_REQUEST);
        } else if (pdfService.checkBookId(bookId)) {
            return new ResponseEntity<>("Сначало удалите загруженный файл!", HttpStatus.BAD_REQUEST);
        }
        try {
            byte[] content = file.getBytes();
            pdfService.save(content, bookId);
            return new ResponseEntity<>("Файл загружен успешно!", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Не удалось загрузить файл!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<?> editPdf(@PathVariable int id,
                                     @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Выберите файл!", HttpStatus.BAD_REQUEST);
        }
        try {
            byte[] content = file.getBytes();
            pdfService.editPdf(id, content);
            return new ResponseEntity<>("Файл загружен успешно!", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Не удалось загрузить файл!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<?> deletePdf(@PathVariable int bookId) {
        pdfService.deleteById(bookId);
        return new ResponseEntity<>("Файл удален успешно!", HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<AppErrorResponse> handleException(BookNotFoundException e) {
        AppErrorResponse appErrorResponse = new AppErrorResponse(
                "Книга с таким id не найдена" + "\n" + e
        );

        return new ResponseEntity<>(appErrorResponse, HttpStatus.NOT_FOUND);
    }

}
