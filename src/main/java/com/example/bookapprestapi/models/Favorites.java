package com.example.bookapprestapi.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Favorite")
@Data
public class Favorites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @ManyToOne
    @JoinColumn(name = "reader_id")
    private Reader reader;
}
