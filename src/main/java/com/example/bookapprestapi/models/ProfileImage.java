package com.example.bookapprestapi.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "reader_image")
@Data
public class ProfileImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_image_id")
    private int imageId;

    @ManyToOne
    @JoinColumn(name = "reader_id", nullable = false)
    private Reader reader;

    @Column(name = "image_content", columnDefinition = "bytea")
    private byte[] content;

}
