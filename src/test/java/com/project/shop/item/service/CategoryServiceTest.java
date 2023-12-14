package com.project.shop.item.service;

import com.project.shop.item.data.CategoryData;
import com.project.shop.item.domain.Category;
import com.project.shop.item.dto.request.CategoryRequest;
import com.project.shop.item.dto.request.CategoryUpdateRequest;
import com.project.shop.item.repository.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
//@Transactional
public class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    static Category category1;
    static Category category2;
    @BeforeEach
    public void before(){
        category1 = CategoryData.createCategory1();
        category2 = CategoryData.createCategory2();

        categoryRepository.save(category1);
        categoryRepository.save(category2);
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
        List<Category> result = categoryService.categoryFindAll();

        //then
        Assertions.assertThat(result.size()).isEqualTo(2);

    }

    @Test
    @DisplayName("카테고리 수정")
    void categoryUpdateTest(){

        //given
        var find1 = categoryRepository.findById(category1.getCategoryId())
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_CATEGORY1_TEST"));
        var find2 = categoryRepository.findById(category2.getCategoryId())
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_CATEGORY2_TEST"));

        CategoryUpdateRequest categoryUpdateRequest1 = new CategoryUpdateRequest("auth", "런닝화", "뉴발란스");
        CategoryUpdateRequest categoryUpdateRequest2 = new CategoryUpdateRequest("auth", "샌들", "나이키");

        //when
        var updateId1 = categoryService.update(find1.getCategoryId(), categoryUpdateRequest1);
        var updateId2 = categoryService.update(find2.getCategoryId(), categoryUpdateRequest2);

        //then
        Assertions.assertThat(updateId1).isEqualTo(1);
        Assertions.assertThat(updateId2).isEqualTo(2);
    }

    @Test
    @DisplayName("카테고리 삭제")
    void categoryDeleteTest(){

        categoryService.delete(category1.getCategoryId());

        Assertions.assertThat(categoryRepository.count()).isEqualTo(1);

    }

}
