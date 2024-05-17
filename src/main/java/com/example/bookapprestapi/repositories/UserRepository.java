package com.example.bookapprestapi.repositories;

import com.example.bookapprestapi.models.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Reader, Integer> {
    Optional<Reader> findByUsername (String username);
}
