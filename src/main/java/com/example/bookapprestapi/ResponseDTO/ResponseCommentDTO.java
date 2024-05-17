package com.example.bookapprestapi.ResponseDTO;

import lombok.Data;

@Data
public class ResponseCommentDTO {
    private String comment;
    private String username;
    private long timestamp;
}
