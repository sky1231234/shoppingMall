package com.project.shop.item.Builder;

import com.project.shop.item.domain.Category;
import com.project.shop.item.dto.request.CategoryRequest;

import java.time.LocalDateTime;


public class CategoryBuilder {

    static LocalDateTime now = LocalDateTime.now();

    public static Category createCategory1(){

        return Category.builder()
                .categoryName("운동화")
                .brandName("나이키")
                .insertDate(now)
                .updateDate(now)
                .build();
    }

    public static Category createCategory2(){

        return Category.builder()
                .categoryName("스니커즈")
                .brandName("뉴발란스")
                .insertDate(now)
                .updateDate(now)
                .build();
    }

    public static Category createCategory3(){

        return Category.builder()
                .categoryName("운동화")
                .brandName("뉴발란스")
                .insertDate(now)
                .updateDate(now)
                .build();
    }

    public static CategoryRequest createCategoryRequest1(){

        return new CategoryRequest(
                "auth",
                "운동화",
                "나이키");

    }
}
