package com.example.bookapprestapi.services;

import com.example.bookapprestapi.models.Favorites;
import com.example.bookapprestapi.repositories.FavoritesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoritesService {
    private final FavoritesRepository favoritesRepository;

    public FavoritesService(FavoritesRepository favoritesRepository) {
        this.favoritesRepository = favoritesRepository;
    }

    public List<Favorites> findAll(int id) {
        return favoritesRepository.findByReaderReaderId(id);
    }

    public void addToFavorites(Favorites favorites) {
        favoritesRepository.save(favorites);
    }

    public void deleteFormFavorites(int readerId, int bookId) {
        favoritesRepository.delete(favoritesRepository.findByReaderReaderIdAndBook_BookId(readerId, bookId));
    }
}
