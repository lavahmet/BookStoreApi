package com.example.bookapprestapi.ResponseDTO;

import lombok.Data;

@Data
public class ResponseFavoriteDTO {
    private ResponseReaderDTO responseReaderDTO;
    private ResponseBookDTO responseBookDTO;
}
