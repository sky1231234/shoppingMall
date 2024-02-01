package com.project.shop.item.service;

import com.project.shop.common.service.ServiceCommon;
import com.project.shop.item.builder.CategoryBuilder;
import com.project.shop.item.domain.Category;
import com.project.shop.item.dto.request.CategoryRequest;
import com.project.shop.item.dto.request.CategoryUpdateRequest;
import com.project.shop.item.dto.response.CategoryResponse;
import com.project.shop.item.repository.CategoryRepository;
import com.project.shop.member.builder.MemberBuilder;
import com.project.shop.member.domain.Authority;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

public class CategoryServiceTest extends ServiceCommon {

    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryRepository categoryRepository;

    Category category1; Category category2; Category category3;
    @BeforeEach
    public void before(){

        //user
        MemberBuilder memberBuilder = new MemberBuilder(passwordEncoder);
        member1 = memberBuilder.signUpMember();
        member2 = memberBuilder.signUpAdminMember();
        var memberSave = memberRepository.save(member1);
        var adminSave = memberRepository.save(member2);

        //auth
        Authority auth = memberBuilder.auth(memberSave);
        Authority authAdmin = memberBuilder.authAdmin(adminSave);
        authorityRepository.save(auth);
        authorityRepository.save(authAdmin);

        category1 = CategoryBuilder.createCategory1();
        category2 = CategoryBuilder.createCategory2();
        category3 = CategoryBuilder.createCategory3();

        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
    }

    @Test
    @DisplayName("카테고리 등록")
    void categoryCreateTest(){

        //given
        CategoryRequest categoryRequest1 = CategoryBuilder.createCategoryRequest1();

        //when
        var category1 = categoryService.create(member2.getLoginId(), categoryRequest1);

        //then
        Assertions.assertThat(category1).isEqualTo(4);

    }

    @Test
    @DisplayName("카테고리 조회")
    void categoryFindAllTest(){

        //when
        List<CategoryResponse> result = categoryService.categoryFindAll(member2.getLoginId());

        //then
        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result.get(0).getCategoryName()).isEqualTo("스니커즈");
        Assertions.assertThat(result.get(1).getBrand().get(0).getBrandName()).isEqualTo("나이키");

    }

    @Test
    @DisplayName("카테고리 수정")
    void categoryUpdateTest(){

        //given
        var find1 = categoryRepository.findById(category1.getCategoryId())
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_CATEGORY1_TEST"));
        CategoryUpdateRequest categoryUpdateRequest1 = CategoryBuilder.createCategoryUpdateRequest();

        //when
        var updateId1 = categoryService.update(member2.getLoginId(), find1.getCategoryId(), categoryUpdateRequest1);

        //then
        Assertions.assertThat(updateId1).isEqualTo(1);
    }

    @Test
    @DisplayName("카테고리 삭제")
    void categoryDeleteTest(){

        categoryService.delete(member2.getLoginId(), category1.getCategoryId());

        Assertions.assertThat(categoryRepository.count()).isEqualTo(2L);

    }

}
