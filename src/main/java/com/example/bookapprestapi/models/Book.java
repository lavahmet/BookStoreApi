package com.example.bookapprestapi.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Book")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private Double price;
    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(name = "comments_count")
    private int commentsCount;
    @Column(name = "views_Count")
    private int viewsCount;
    @Column(name = "downloads_count")
    private int downloadsCount;

    @Transient
    public String getCategoryName() {
        return category != null ? category.getCategoryName() : null;
    }

}
