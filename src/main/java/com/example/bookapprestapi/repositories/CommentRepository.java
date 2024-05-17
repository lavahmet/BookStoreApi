package com.example.bookapprestapi.repositories;

import com.example.bookapprestapi.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByBook_BookId(int id);
}
