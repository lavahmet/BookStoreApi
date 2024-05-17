package com.example.bookapprestapi.RequestDTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RequestCommentDTO {
    @NotEmpty
    private String comment;
}
