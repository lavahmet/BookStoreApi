package com.example.bookapprestapi.controllers;

import com.example.bookapprestapi.RequestDTO.RequestFavoriteDTO;
import com.example.bookapprestapi.models.Book;
import com.example.bookapprestapi.models.Favorites;
import com.example.bookapprestapi.models.Reader;
import com.example.bookapprestapi.services.BookService;
import com.example.bookapprestapi.services.FavoritesService;
import com.example.bookapprestapi.services.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorites")
public class FavoritesController {

    private final FavoritesService favoritesService;
    private final BookService bookService;
    private final ReaderService readerService;

    @PostMapping("/add/favorite")
    public ResponseEntity<HttpStatus> addToFavorite(@RequestBody RequestFavoriteDTO favoriteDTO) {
        favoritesService.addToFavorites(convertDTOtoModel(favoriteDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteFromFavorite(@RequestBody RequestFavoriteDTO favoriteDTO) {
        favoritesService.deleteFormFavorites(favoriteDTO.getReaderId(), favoriteDTO.getBookId());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Favorites convertDTOtoModel(RequestFavoriteDTO requestFavoriteDTO) {
        Book book = bookService.findById(requestFavoriteDTO.getBookId());
        Reader reader = readerService.findById(requestFavoriteDTO.getBookId());
        Favorites favorites = new Favorites();
        favorites.setBook(book);
        favorites.setReader(reader);
        return favorites;
    }








}
