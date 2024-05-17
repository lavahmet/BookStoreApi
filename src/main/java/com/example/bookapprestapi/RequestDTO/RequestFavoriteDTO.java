package com.example.bookapprestapi.RequestDTO;

import lombok.Data;

@Data
public class RequestFavoriteDTO {
    private int bookId;
    private int readerId;
}
