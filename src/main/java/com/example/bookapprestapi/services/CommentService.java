package com.example.bookapprestapi.services;

import com.example.bookapprestapi.models.Comment;
import com.example.bookapprestapi.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment findById(int id) {
        Optional<Comment> foundedComment = commentRepository.findById(id);
        return foundedComment.orElse(null);
    }

    public List<Comment> findByBookId(int id) {
        return commentRepository.findAllByBook_BookId(id);
    }


    @Transactional
    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Transactional
    public Comment editComment(int id, Comment comment) {
        comment.setCommentId(id);
        commentRepository.save(comment);
        return findById(id);
    }

    @Transactional
    public void deleteComment(int id) {
        commentRepository.deleteById(id);
    }

}
