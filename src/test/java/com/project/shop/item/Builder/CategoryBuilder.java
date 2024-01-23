package com.project.shop.item.Builder;

import com.project.shop.item.domain.Category;
import com.project.shop.item.dto.request.CategoryRequest;
import com.project.shop.item.dto.request.CategoryUpdateRequest;

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

    public static Category createCategory4(){

        return Category.builder()
                .categoryName("운동화")
                .brandName("아디다스")
                .insertDate(now)
                .updateDate(now)
                .build();
    }

    public static CategoryRequest createCategoryRequest1(){

        return new CategoryRequest(
                "admin",
                "슬리퍼",
                "나이키");

    }

    public static CategoryRequest createCategoryRequest2(){

        return new CategoryRequest(
                "admin",
                "운동화",
                "아디다스"
        );
    }

    public static CategoryUpdateRequest createCategoryUpdateRequest(){

        return new CategoryUpdateRequest(
                "admin",
                "스니커즈",
                "뉴발란스"
        );
    }
}
