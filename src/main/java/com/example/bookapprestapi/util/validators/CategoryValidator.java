package com.example.bookapprestapi.util.validators;

import com.example.bookapprestapi.models.Category;
import com.example.bookapprestapi.services.CategoryService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CategoryValidator implements Validator {
    private final CategoryService categoryService;

    public CategoryValidator(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Category.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Category category = (Category) target;

        if (categoryService.findByCategoryName(category.getCategoryName()) != null) {
            errors.rejectValue("categoryName", "", "Такая категория уже сушествует");
        }
    }
}
