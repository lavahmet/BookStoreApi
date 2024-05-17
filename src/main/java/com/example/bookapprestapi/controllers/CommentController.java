package com.example.bookapprestapi.controllers;

import com.example.bookapprestapi.ResponseDTO.ResponseCommentDTO;
import com.example.bookapprestapi.models.Comment;
import com.example.bookapprestapi.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @GetMapping("/")
    public List<ResponseCommentDTO> getAll() {
        return commentService.findAll().stream()
                .map(this::convertModelToDTO).collect(Collectors.toList());
    }

    @GetMapping("/by/book/{id}/")
    public List<ResponseCommentDTO> getAllByBookId(@PathVariable int id) {
        return commentService.findByBookId(id).stream()
                .map(this::convertModelToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}/")
    public ResponseCommentDTO getAllById(@PathVariable int id) {
        return convertModelToDTO(commentService.findById(id));
    }

    @DeleteMapping("/delete/{id}/")
    public void deleteBook(@PathVariable int id) {
        commentService.deleteComment(id);
    }

    public ResponseCommentDTO convertModelToDTO(Comment comment) {
        return modelMapper.map(comment, ResponseCommentDTO.class);
    }

}
