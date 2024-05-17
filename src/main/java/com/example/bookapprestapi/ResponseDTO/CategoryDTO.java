package com.example.bookapprestapi.ResponseDTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CategoryDTO {
    @NotEmpty
    @Column(unique = true)
    private String categoryName;
}
