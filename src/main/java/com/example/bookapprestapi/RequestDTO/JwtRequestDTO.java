package com.example.bookapprestapi.RequestDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class JwtRequestDTO {
    @NotEmpty
    private String username;
    @NotEmpty
    @Min(value = 8)
    private String password;
}
