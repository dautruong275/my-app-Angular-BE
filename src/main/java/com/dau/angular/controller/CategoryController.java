package com.dau.angular.controller;

import com.dau.angular.dto.CategoryDTO;
import com.dau.angular.response.BaseResponse;
import com.dau.angular.response.category.CategoryListResponse;
import com.dau.angular.response.category.CategoryResponses;
import com.dau.angular.service.category.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;
    @PostMapping("")
    public ResponseEntity<BaseResponse<CategoryDTO>> createCategory(@RequestBody CategoryDTO categoryDTO) {

        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                BaseResponse.<CategoryDTO>builder()
                        .status(HttpStatus.CREATED)
                        .message("Tạo danh mục thành công")
                        .data(createdCategory)
                        .build()
        );
    }
    @GetMapping
    public ResponseEntity<BaseResponse<List<CategoryDTO>>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(
                BaseResponse.<List<CategoryDTO>>builder()
                        .status(HttpStatus.OK)
                        .message("Lấy danh sách danh mục thành công")
                        .data(categories)
                        .build()
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<CategoryDTO>> getCategoryById(@PathVariable Long id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(
                BaseResponse.<CategoryDTO>builder()
                        .status(HttpStatus.OK)
                        .message("Lấy danh mục thành công")
                        .data(category)
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<CategoryDTO>> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(
                BaseResponse.<CategoryDTO>builder()
                        .status(HttpStatus.OK)
                        .message("Cập nhật danh mục thành công")
                        .data(updatedCategory)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(
                BaseResponse.<Void>builder()
                        .status(HttpStatus.NO_CONTENT)
                        .message("Xóa danh mục thành công")
                        .data(null)
                        .build()
        );
    }
    @GetMapping("/search")
    public ResponseEntity<?> searchCategories(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String createdBy,
            @RequestParam(required = false) String fromDate, // Định dạng: "yyyy-MM-dd'T'HH:mm:ss"
            @RequestParam(required = false) String toDate,   // Định dạng: "yyyy-MM-dd'T'HH:mm:ss"
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        int totalPages = 0;

        List<CategoryResponses> categoryResponses;
        logger.info(String.format("name = %s, ",name));
        Page<CategoryResponses> categoryPage = categoryService.searchCategories(name, pageable);
        //Tổng số trang
        totalPages = categoryPage.getTotalPages();
        categoryResponses = categoryPage.getContent();
        for (CategoryResponses product : categoryResponses) {
            product.setTotalPages(totalPages);
        }
        CategoryListResponse  categoryListResponse = CategoryListResponse
                .builder()
                .products(categoryResponses)
                .totalPages(totalPages)
                .build();
        return ResponseEntity.ok().body(BaseResponse.builder()
                .message("Get products successfully")
                .status(HttpStatus.OK)
                .data(categoryListResponse)
                .build());
    }
}
