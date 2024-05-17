package com.example.bookapprestapi.repositories;

import com.example.bookapprestapi.models.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorites, Integer> {
//
//    @Modifying
//    @Query("delete from Favorites where book.bookId = :bookId and reader.readerId = :readerId")
//    void deleteBookByBook_BookIdAndReaderReaderId(int bookId, int readerId);


    @Modifying
    @Query("delete from Favorites where book.bookId = :bookId and reader.readerId = :readerId")
    void delete(int readerId, int bookId);

//    @Query("select Favorites from Favorites where book.bookId = :bookId and reader.readerId = :readerId")
    Favorites findByReaderReaderIdAndBook_BookId(int readerId, int bookId);

    List<Favorites> findByReaderReaderId(int id);
}
