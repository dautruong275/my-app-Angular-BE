package com.dau.angular.response.category;

import com.dau.angular.entity.Category;
import com.dau.angular.response.ResponseUtil;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponses extends ResponseUtil {

    private Long id;
    private String name;
    private String description;
    // Thêm trường totalPages
    private int totalPages;

    public static CategoryResponses fromProduct(Category category) {

        CategoryResponses productResponse = CategoryResponses.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .totalPages(0)
                .build();
        productResponse.setCreatedAt(category.getCreatedAt());
        productResponse.setUpdatedAt(category.getUpdatedAt());
        return productResponse;
    }
}
