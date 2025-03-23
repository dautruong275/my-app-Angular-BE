package com.dau.angular.controller;

import com.dau.angular.dto.CategoryDTO;
import com.dau.angular.entity.Category;
import com.dau.angular.response.BaseResponse;
import com.dau.angular.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/")
    public ResponseEntity<BaseResponse<Category>> createCateogry(@RequestBody CategoryDTO categoryDTO){

        Category category = categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok(
                BaseResponse.<Category>builder()
                        .status(HttpStatus.CREATED)
                        .message("Tạo danh mục thành công")
                        .data(category)
                        .build()
        );
    }
}
