package com.example.bookapprestapi.RequestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RequestReaderDTO {
    @NotEmpty
    private String username;
    @NotEmpty
    @Email
    private String email;
    private String profileImage;
}
