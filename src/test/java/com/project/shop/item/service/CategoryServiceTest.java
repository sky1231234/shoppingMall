package com.project.shop.item.service;

import com.project.shop.item.domain.Category;
import com.project.shop.item.dto.request.CategoryRequest;
import com.project.shop.item.dto.request.CategoryUpdateRequest;
import com.project.shop.item.dto.response.CategoryResponse;
import com.project.shop.item.repository.CategoryRepository;
import com.project.shop.item.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
//@Transactional
public class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    Category cate1 = null;
    Category cate2 = null;
    @BeforeEach
    public void before(){

        LocalDateTime now = LocalDateTime.now();

        cate1 = Category.builder()
                .categoryName("운동화")
                .brandName("나이키")
                .insertDate(now)
                .updateDate(now)
                .build();

        cate2 = Category.builder()
                .categoryName("스니커즈")
                .brandName("뉴발란스")
                .insertDate(now)
                .updateDate(now)
                .build();

        //when
        categoryRepository.save(cate1);
        categoryRepository.save(cate2);

    }

    @Test
    @DisplayName("카테고리 등록")
    void categoryCreateTest(){

        //given
        CategoryRequest categoryRequest1 = new CategoryRequest("auth", "런닝화", "나이키");
        CategoryRequest categoryRequest2 = new CategoryRequest("auth", "운동화", "뉴발란스");

        //when
        var category1 = categoryService.create(categoryRequest1);
        var category2 = categoryService.create(categoryRequest2);

        //then
        Assertions.assertThat(category1).isEqualTo(3);
        Assertions.assertThat(category2).isEqualTo(4);

    }

    @Test
    @DisplayName("카테고리 조회")
    void categoryFindAllTest(){

        //when
        List<CategoryResponse> result = categoryService.categoryFindAll();

        //then
        Assertions.assertThat(result.size()).isEqualTo(2);

    }

    @Test
    @DisplayName("카테고리 수정")
    void categoryUpdateTest(){

        //given
        var find1 = categoryRepository.findById(cate1.getCategoryId());
        var find2 = categoryRepository.findById(cate2.getCategoryId());

        CategoryUpdateRequest categoryUpdateRequest1 = new CategoryUpdateRequest("auth", "런닝화", "뉴발란스");
        CategoryUpdateRequest categoryUpdateRequest2 = new CategoryUpdateRequest("auth", "샌들", "나이키");

        //when
        var updateId1 = categoryService.update(find1.get().getCategoryId(), categoryUpdateRequest1);
        var updateId2 = categoryService.update(find2.get().getCategoryId(), categoryUpdateRequest2);

        //then
        Assertions.assertThat(updateId1).isEqualTo(1);
        Assertions.assertThat(updateId2).isEqualTo(2);
    }

    @Test
    @DisplayName("카테고리 삭제")
    void categoryDeleteTest(){

        categoryService.delete(cate1.getCategoryId());

        Assertions.assertThat(categoryRepository.count()).isEqualTo(1);

    }

}
