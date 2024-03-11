package com.project.shop.item.controller;

import com.project.shop.common.controller.ControllerCommon;
import com.project.shop.item.builder.CategoryFixture;
import com.project.shop.item.domain.Category;
import com.project.shop.item.dto.request.CategoryRequest;
import com.project.shop.item.dto.request.CategoryUpdateRequest;
import com.project.shop.item.repository.CategoryRepository;
import com.project.shop.member.builder.MemberBuilder;
import com.project.shop.member.domain.Authority;
import com.project.shop.mock.WithCustomMockUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest extends ControllerCommon {

    @Autowired
    CategoryRepository categoryRepository;
    Category category1; Category category2; Category category3;

    LocalDateTime now = LocalDateTime.now();
    @BeforeEach
    void beforeEach(){

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

        //category
        category1 = CategoryFixture.createCategory(1L, "운동화", "나이키", now );
        category2 = CategoryFixture.createCategory2();
        category3 = CategoryFixture.createCategory3();
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
    }

    @Test
    @WithCustomMockUser(loginId = "admin",authority = "admin")
    @DisplayName("카테고리 조회")
    void categoryFindAll() throws Exception {
        //given

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].categoryName").value("스니커즈"))
                .andExpect(jsonPath("$[1].brand[1].brandName").value("뉴발란스"));

    }

    @Test
    @WithCustomMockUser(loginId = "admin",authority = "admin")
    @DisplayName("카테고리 등록")
    void categoryCreate() throws Exception {
        //given
        CategoryRequest categoryRequest = CategoryFixture.createCategoryRequest1();

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/categories")
                .content(objectMapper.writeValueAsString(categoryRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        //then
        Assertions.assertThat(categoryRepository.findById(4L).get()
                .getCategoryName()).isEqualTo("슬리퍼");
    }

    @Test
    @WithCustomMockUser(loginId = "admin",authority = "admin")
    @DisplayName("카테고리 수정")
    void categoryUpdate() throws Exception {
        //given
        CategoryUpdateRequest categoryUpdateRequest = CategoryFixture.createCategoryUpdateRequest();

        //when
        mockMvc.perform(MockMvcRequestBuilders.put("/admin/categories/{categoryId}",1)
                        .content(objectMapper.writeValueAsString(categoryUpdateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        Assertions.assertThat(categoryRepository.findById(1L).get().getCategoryName()).isEqualTo("슬리퍼");
    }

    @Test
    @WithCustomMockUser(loginId = "admin",authority = "admin")
    @DisplayName("카테고리 삭제")
    void categoryDelete() throws Exception {
        //given

        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/categories/{categoryId}",1))
                .andExpect(status().isNoContent());

        //then
        Assertions.assertThat(categoryRepository.findAll().size()).isEqualTo(2);
    }

}
