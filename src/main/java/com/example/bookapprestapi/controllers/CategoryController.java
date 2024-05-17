package com.example.bookapprestapi.controllers;

import com.example.bookapprestapi.ResponseDTO.CategoryDTO;
import com.example.bookapprestapi.models.Category;
import com.example.bookapprestapi.services.CategoryService;
import com.example.bookapprestapi.util.exceptions.AppErrorResponse;
import com.example.bookapprestapi.util.exceptions.CategoryNotAddedException;
import com.example.bookapprestapi.util.exceptions.CategoryNotFoundException;
import com.example.bookapprestapi.util.validators.CategoryValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final CategoryValidator categoryValidator;

    @GetMapping("/")
    public List<CategoryDTO> getAllCategories() {
        return categoryService.findAll().stream()
                .map(this::convertModelToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CategoryDTO getBookById(@PathVariable int id) {
        return convertModelToDTO(categoryService.findById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addCategory(@RequestBody @Valid CategoryDTO categoryDTO,
                                                  BindingResult bindingResult) {
        categoryValidator.validate(convertDTOtoModel(categoryDTO), bindingResult);
        checkBindingResult(bindingResult);
        categoryService.addCategory(convertDTOtoModel(categoryDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/edit/{id}")
    public CategoryDTO editCategory(@PathVariable int id, @RequestBody @Valid CategoryDTO categoryDTO,
                                    BindingResult bindingResult) {
        categoryValidator.validate(convertDTOtoModel(categoryDTO), bindingResult);
        checkBindingResult(bindingResult);
        return convertModelToDTO(categoryService.updateCategory(id, convertDTOtoModel(categoryDTO)));

    }

    private void checkBindingResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errMsg = new StringBuilder();
            List<FieldError> errorList = bindingResult.getFieldErrors();
            for (FieldError error : errorList) {
                errMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }

            throw new CategoryNotAddedException(errMsg.toString());
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable int id) {
        categoryService.deleteById(id);
    }

    @ExceptionHandler
    private ResponseEntity<AppErrorResponse> handleException(CategoryNotFoundException e) {
        AppErrorResponse appErrorResponse = new AppErrorResponse(
                "Категория с таким id не найдена"
        );

        return new ResponseEntity<>(appErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<AppErrorResponse> handleException(CategoryNotAddedException e) {
        AppErrorResponse appErrorResponse = new AppErrorResponse(
                e.getMessage()
        );
        return new ResponseEntity<>(appErrorResponse, HttpStatus.BAD_REQUEST);
    }

    private CategoryDTO convertModelToDTO(Category category) {
        if (category == null) {
            throw new CategoryNotFoundException("");
        }
        return modelMapper.map(category, CategoryDTO.class);
    }

    private Category convertDTOtoModel(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, Category.class);
    }
}
