package com.example.bookapprestapi.services;

import com.example.bookapprestapi.models.Category;
import com.example.bookapprestapi.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(int id) {
        Optional<Category> foundedCategory = categoryRepository.findById(id);
        return foundedCategory.orElse(null);
    }

    @Transactional
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(int id, Category category) {
        if (categoryRepository.existsById(id)) {
            category.setCategoryId(id);
            categoryRepository.save(category);
            return findById(id);
        }
        return null;
    }

    @Transactional
    public void deleteById(int id) {
        categoryRepository.updateBooksCategoryIdToNull(id);
        categoryRepository.deleteCategoryById(id);
    }

    public Category findByCategoryName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }
}
