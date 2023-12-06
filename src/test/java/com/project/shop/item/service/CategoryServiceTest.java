package com.project.shop.item.service;

import com.project.shop.item.domain.Category;
import com.project.shop.item.repository.CategoryRepository;
import com.project.shop.item.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;


    @Test
    @DisplayName("카테고리 등록")
    void categoryCreateTest(){
//        var result = categoryService.categoryFindAll();
//
//        Assertions.assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("카테고리 조회")
    void categoryFindAllTest(){
        var result = categoryService.categoryFindAll();

        Assertions.assertThat(result).isNotEmpty();
    }
}
