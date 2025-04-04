package com.dau.angular.service.category;

import com.dau.angular.dto.CategoryDTO;
import com.dau.angular.response.category.CategoryResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICategoryService {
    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryById(Long id);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    void deleteCategory(Long id);

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

    Page<CategoryResponses> searchCategories(String name, Pageable pageable);



}
