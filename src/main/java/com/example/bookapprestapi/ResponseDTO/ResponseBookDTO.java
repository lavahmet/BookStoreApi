package com.example.bookapprestapi.ResponseDTO;

import lombok.Data;

import java.util.List;

@Data
public class ResponseBookDTO {
    private String title;
    private String author;
    private String description;
    private double price;
    private String categoryName;
    private List<ResponseCommentDTO> responseCommentDTO;
    private int commentsCount;
    private int viewsCount;
    private int downloadsCount;

}
