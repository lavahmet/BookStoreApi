package com.example.bookapprestapi.repositories;

import com.example.bookapprestapi.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByCategoryName(String categoryName);

    @Modifying
    @Query("Update Book set category = null where category.categoryId = :id")
    void updateBooksCategoryIdToNull (int id);

    @Modifying
    @Query(value = "delete from Category where categoryId = :id")
    void deleteCategoryById(int id);
}
