package com.project.shop.member.controller;

import com.project.shop.common.controller.ControllerCommon;
import com.project.shop.item.builder.CategoryBuilder;
import com.project.shop.item.builder.ItemBuilder;
import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Option;
import com.project.shop.item.repository.CategoryRepository;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.OptionRepository;
import com.project.shop.member.builder.CartBuilder;
import com.project.shop.member.builder.MemberBuilder;
import com.project.shop.member.domain.Authority;
import com.project.shop.member.domain.Cart;
import com.project.shop.member.dto.request.CartRequest;
import com.project.shop.member.dto.request.CartUpdateRequest;
import com.project.shop.member.repository.CartRepository;
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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class CartControllerTest extends ControllerCommon {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OptionRepository optionRepository;

    Cart cart;
    Item item1; Item item2;
    Option option1; Option option2;
    @BeforeEach
    void beforeEach(){

        //user
        MemberBuilder memberBuilder = new MemberBuilder(passwordEncoder);
        member1 = memberBuilder.signUpMember();
        var memberSave = memberRepository.save(member1);

        Authority auth = memberBuilder.auth(memberSave);
        authorityRepository.save(auth);

        //category
        Category category = CategoryBuilder.createCategory1();
        categoryRepository.save(category);

        //item
        item1 = ItemBuilder.createItem1(category);
        item2 = ItemBuilder.createItem2(category);
        itemRepository.save(item1);
        itemRepository.save(item2);

        //option
        option1 = ItemBuilder.createOption1(item1);
        option2 = ItemBuilder.createOption2(item1);
        optionRepository.save(option1);
        optionRepository.save(option2);

        //cart
        cart = CartBuilder.createCart1(member1,item1);
        cartRepository.save(cart);
    }

    @Test
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    @DisplayName("회원별 장바구니 조회")
    void cartFindByUser() throws Exception {
        //given

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/members/carts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*",hasSize(1)))
                .andExpect(jsonPath("$[0].itemColor").value("검정"));

    }

    @Test
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    @DisplayName("장바구니 새로 등록")
    void cartCreate1() throws Exception {

        //given

        CartRequest cartRequest = CartBuilder.createNewCart();

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/carts")
                        .content(objectMapper.writeValueAsString(cartRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        Assertions.assertThat(cartRepository.findAll().size()).isEqualTo(2);
        Assertions.assertThat(cartRepository.findById(2L).get().getCount()).isEqualTo(2);

    }

    @Test
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    @DisplayName("등록되어있던 장바구니 추가 등록")
    void cartCreate() throws Exception {
        //given

        CartRequest cartRequest = CartBuilder.createAlreadyCart();

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/carts")
                        .content(objectMapper.writeValueAsString(cartRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        Assertions.assertThat(cartRepository.findAll().size()).isEqualTo(1);
        Assertions.assertThat(cartRepository.findById(1L).get().getCount()).isEqualTo(5);
    }

    @Test
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    @DisplayName("장바구니 수정")
    void cartUpdate() throws Exception {
        //given
        CartUpdateRequest cartUpdateRequest = CartBuilder.createCartUpdateRequest();

        //when
        mockMvc.perform(MockMvcRequestBuilders.put("/api/carts/{cartId}",1)
                        .content(objectMapper.writeValueAsString(cartUpdateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        Assertions.assertThat(cartRepository.findById(1L).get().getOptionId()).isEqualTo(2);
    }

    @Test
    @WithCustomMockUser(loginId = "loginId",authority = "user")
    @DisplayName("장바구니 삭제")
    void addressDelete() throws Exception {
        //given
        Cart cart = CartBuilder.createCart1(member1,item2);
        cartRepository.save(cart);

        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/carts/{cartId}",1))
                .andExpect(status().isNoContent());

        //then
        Assertions.assertThat(cartRepository.findAll().size()).isEqualTo(1);
    }

}
