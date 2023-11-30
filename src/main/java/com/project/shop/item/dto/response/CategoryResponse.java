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

//    {
//        "categoryId" : 1,
//        "category" : [
//                  {
//                  "categoryName" : "나이키",
//                  }
//              ]
//    }

    private long categoryId;
    private String categoryName;
    private String brandName;

    //카테고리(브랜드별) 조회
    public static CategoryResponse fromEntity(Category category){

        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .build();
    }

}