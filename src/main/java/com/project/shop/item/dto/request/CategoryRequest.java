package com.project.shop.item.dto.request;

import com.project.shop.item.domain.Category;

import javax.validation.constraints.NotBlank;

public record CategoryRequest(
        @NotBlank String auth,
        @NotBlank String categoryName,
        @NotBlank String brandName
        ) {

        public Category toEntity(){
                return Category.builder()
                        .categoryName(this.categoryName())
                        .brandName(this.brandName())
                        .build();
        }

}
