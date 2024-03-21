package com.project.shop.item.dto.request;

import com.project.shop.item.domain.Category;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public record CategoryUpdateRequest(
        @NotBlank String auth,
        @NotBlank String categoryName,
        @NotBlank String brandName
        ) {

        public Category toEntity(){

                return Category.builder()
                        .categoryName(categoryName)
                        .brandName(brandName)
                        .dateTime(LocalDateTime.now())
                        .build();
        }
}
