package com.example.bookapprestapi.util.exceptions;

public class CommentNotAdded extends RuntimeException{
    public CommentNotAdded(String message) {
        super(message);
    }
}
