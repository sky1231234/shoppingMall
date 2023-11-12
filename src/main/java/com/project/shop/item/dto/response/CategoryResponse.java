package com.project.shop.item.dto.response;

import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {
    private String categoryName;
    private String brandName;

    public static CategoryResponse fromEntity(Category category){

        return CategoryResponse.builder()
                .categoryName(category.getCategoryName())
                .brandName(category.getBrandName())
                .build();
    }
}