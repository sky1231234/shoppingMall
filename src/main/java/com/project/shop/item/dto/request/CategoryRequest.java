package com.project.shop.item.dto.request;

import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public record CategoryRequest(
        @NotBlank String auth,
        @NotBlank String categoryName,
        @NotBlank String brandName
        ) {

        public Category toEntity(CategoryRequest categoryRequest){

                return Category.builder()
                        .categoryName(categoryRequest.categoryName())
                        .brandName(categoryRequest.getBrandName())
                        .build();
        }

}
