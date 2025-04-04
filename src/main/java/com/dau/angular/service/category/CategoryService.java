package com.dau.angular.service.category;

import com.dau.angular.dto.CategoryDTO;
import com.dau.angular.entity.Category;
import com.dau.angular.mapper.CategoryMapper;
import com.dau.angular.repository.CategoryRepository;
import com.dau.angular.response.category.CategoryResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id " + id));
        return categoryMapper.entityToDto(category);
    }

    @Override
    @Transactional
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = Category
                .builder()
                .name(categoryDTO.getName())
                .createdBy(categoryDTO.getCreatedBy())
                .isActive(categoryDTO.getIsActive())
                .description(categoryDTO.getDescription())
                .build();
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.entityToDto(savedCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id " + id));
        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new RuntimeException("Danh mục không tồn tại!");
        }
        Category category = optionalCategory.get();
        //category.setName(categoryDTO.getName());
        category.setActive(categoryDTO.getIsActive());
        categoryMapper.updateFromDto(categoryDTO, category);
        Category updatedCategory = categoryRepository.save(category);

        return categoryMapper.entityToDto(updatedCategory);
    }

    @Override
    public Page<CategoryResponses> searchCategories(String name, Pageable pageable) {
        Page<Category> categoryPage = categoryRepository.searchCategories(name, pageable);
        return categoryPage.map(CategoryResponses::fromProduct);
    }
}
