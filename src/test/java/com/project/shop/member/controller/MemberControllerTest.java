package com.project.shop.member.controller;

import com.project.shop.item.Builder.CategoryBuilder;
import com.project.shop.item.Builder.ItemBuilder;
import com.project.shop.item.domain.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {

    @BeforeEach
    void beforeEach(){

    }
    @Test
    @DisplayName("상품 전체 조회 테스트")
    void items_get_test() throws Exception {
        //given
        //when

        //then

    }
}
