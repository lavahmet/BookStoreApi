package com.example.bookapprestapi.ResponseDTO;

import lombok.Data;

import java.util.List;

@Data
public class ResponseReaderDTO {
    private String username;
    private String email;
    private String password;
    private String profileImage;
    private List<ResponseBookDTO> bookDTOList;
}
