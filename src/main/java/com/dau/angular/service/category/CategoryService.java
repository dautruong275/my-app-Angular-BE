package com.dau.angular.service.category;

import com.dau.angular.dto.CategoryDTO;
import com.dau.angular.entity.Category;
import com.dau.angular.mapper.CategoryMapper;
import com.dau.angular.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    @Transactional
    public Category createCategory(CategoryDTO categoryDTO) {
        Category newCategory = Category
                .builder()
                .name(categoryDTO.getName())
                .build();
        return categoryRepository.save(categoryMapper.dtoToEntity(categoryDTO));
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Category updateCategory(Long id, CategoryDTO categoryDTO) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new RuntimeException("Danh mục không tồn tại!");
        }
        Category category = optionalCategory.get();
        category.setName(categoryDTO.getName());
        category.setActive(categoryDTO.getIsActive());
        categoryRepository.save(category);
        return categoryRepository.save(category);
    }

    @Override
    public Page<Category> searchCategories(String name, String createdBy, LocalDateTime fromDate, LocalDateTime toDate, String status, Pageable pageable) {
        return null;
    }
}
