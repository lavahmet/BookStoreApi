package com.example.bookapprestapi.RequestDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class RequestBookDTO {
    @NotEmpty
    private String title;
    @NotEmpty
    private String author;
    @NotEmpty
    private String description;
    @NotEmpty
    @Min(value = 0)
    private double price;
    @NotEmpty
    private String categoryName;
}
