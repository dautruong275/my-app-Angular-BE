package com.dau.angular.response.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class CategoryListResponse {
    private List<CategoryResponses> products;
    private int totalPages;
}
