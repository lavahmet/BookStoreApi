package com.example.bookapprestapi.RequestDTO;

import com.example.bookapprestapi.models.Book;
import lombok.Data;

@Data
public class BookPdfRequestDTO {
    private byte[] content;
    private Book book;
}
