package com.example.bookapprestapi.util.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppErrorResponse {
    private String message;
}
