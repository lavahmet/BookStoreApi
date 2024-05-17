package com.example.bookapprestapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Collection;

@Entity
@Table(name = "Reader")
@Data
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reader_id")
    private int readerId;
    @Column(name = "username", unique = true)
    @NotEmpty
    private String username;
    @Column(name = "email")
    @Email
    @NotEmpty
    private String email;
    @Column(name = "password")
    @Min(value = 8)
    @NotEmpty
    private String password;
    @Column(name = "profile_image")
    @Min(value = 8)
    @NotEmpty
    private String profileImage;
    @ManyToMany
    @JoinTable(
            name = "readers_roles",
            joinColumns = @JoinColumn(name = "reader_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles;
//    @ManyToMany
//    @JoinTable(
//            name = "favorite",
//            joinColumns = @JoinColumn(name = "reader_id"),
//            inverseJoinColumns = @JoinColumn(name = "book_id")
//    )
//    private Collection<Book> favorites;
}
