package com.project.shop.item.dto.request;

import com.project.shop.item.domain.Category;
import jdk.jfr.Timestamp;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public record CategoryRequest(
        @NotBlank String auth,
        @NotBlank String categoryName,
        @NotBlank String brandName
        ) {

        public Category toEntity(){

                return Category.builder()
                        .categoryName(this.categoryName())
                        .brandName(this.brandName())
                        .insertDate(LocalDateTime.now())
                        .updateDate(LocalDateTime.now())
                        .build();
        }

}
