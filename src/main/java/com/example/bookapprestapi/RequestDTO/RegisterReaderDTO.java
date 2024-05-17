package com.example.bookapprestapi.RequestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegisterReaderDTO {
    @NotEmpty
    private String username;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Min(value = 8)
    private String password;
    @NotEmpty
    @Min(value = 8)
    private String confirmPassword;
}
