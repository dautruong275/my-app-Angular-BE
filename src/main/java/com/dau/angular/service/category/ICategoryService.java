package com.dau.angular.service.category;

import com.dau.angular.dto.CategoryDTO;
import com.dau.angular.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ICategoryService {
    List<Category> getAllCategories();

    Optional<Category> getCategoryById(Long id);

    Category createCategory(CategoryDTO categoryDTO);

    void deleteCategory(Long id);

    Category updateCategory(Long id, CategoryDTO categoryDTO);

    Page<Category> searchCategories(String name, String createdBy, LocalDateTime fromDate, LocalDateTime toDate, String status, Pageable pageable);



}
