package com.example.bookapprestapi.controllers;

import com.example.bookapprestapi.services.ProfileImageService;
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
@RequestMapping("/profile-image")
@RequiredArgsConstructor
public class ProfileImageController {
    private final ProfileImageService imageService;


    @GetMapping("/{imageId}")
    public ResponseEntity<?> getById(@PathVariable int imageId) {
        byte[] imageContent = imageService.findById(imageId).getContent();
        if (imageContent != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(imageContent);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add/{imageId}")
    public ResponseEntity<?> addPdf(@PathVariable int imageId,
                                    @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Выберите фото!", HttpStatus.BAD_REQUEST);
        } else if (imageService.checkBookId(imageId)) {
            return new ResponseEntity<>("Сначало удалите загруженное фото!", HttpStatus.BAD_REQUEST);
        }
        try {
            byte[] content = file.getBytes();
            imageService.save(content, imageId);
            return new ResponseEntity<>("Фото загружен успешно!", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Не удалось загрузить фото!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<?> editPdf(@PathVariable int id,
                                     @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Выберите фото!", HttpStatus.BAD_REQUEST);
        }
        try {
            byte[] content = file.getBytes();
            imageService.editPdf(id, content);
            return new ResponseEntity<>("Фото загружен успешно!", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Не удалось загрузить фото!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<?> deletePdf(@PathVariable int bookId) {
        imageService.deleteById(bookId);
        return new ResponseEntity<>(" Фото удален успешно!", HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<AppErrorResponse> handleException(BookNotFoundException e) {
        AppErrorResponse appErrorResponse = new AppErrorResponse(
                "Потльзоваиель с таким id не найдена" + "\n" + e
        );

        return new ResponseEntity<>(appErrorResponse, HttpStatus.NOT_FOUND);
    }

}
