package com.project.shop.item.controller;

import com.project.shop.common.controller.ControllerCommon;
import com.project.shop.item.builder.CategoryFixture;
import com.project.shop.item.builder.ItemFixture;
import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.Option;
import com.project.shop.item.dto.request.ItemRequest;
import com.project.shop.item.dto.request.ItemUpdateRequest;
import com.project.shop.item.repository.CategoryRepository;
import com.project.shop.item.repository.ItemImgRepository;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.OptionRepository;
import com.project.shop.member.builder.MemberBuilder;
import com.project.shop.member.domain.Authority;
import com.project.shop.member.repository.PointRepository;
import com.project.shop.mock.WithCustomMockUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest extends ControllerCommon{

    @Autowired
    PointRepository pointRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemImgRepository itemImgRepository;
    @Autowired
    OptionRepository optionRepository;

    Category category1; Category category2; Category category3;
    Item item1;
    ItemImg itemImg1;
    Option option1; Option option2; Option option3;

    LocalDateTime now = LocalDateTime.now();
    @BeforeEach
    void beforeEach(){

        //user
        MemberBuilder memberBuilder = new MemberBuilder(passwordEncoder);
        member1 = memberBuilder.signUpMember();
        var memberSave = memberRepository.save(member1);
        member2 = memberBuilder.signUpAdminMember();
        var memberAdminSave = memberRepository.save(member2);

        Authority auth = memberBuilder.auth(memberSave);
        Authority authAdmin = memberBuilder.authAdmin(memberAdminSave);
        authorityRepository.save(auth);
        authorityRepository.save(authAdmin);

        //category
        category1 = CategoryFixture.createCategory(1L, "운동화", "나이키", now);
        category2 = CategoryFixture.createCategory(2L, "스니커즈", "뉴발란스", now);
        category3 = CategoryFixture.createCategory(3L, "슬리퍼", "아디다스", now);

        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        //item
        item1 =  ItemFixture.createItem(category1, "조던", 1000000, "인기 많음", now);
        itemRepository.save(item1);

        itemImg1 = ItemFixture.createImg1(item1);
        itemImgRepository.save(itemImg1);

        option1 = ItemFixture.createOption1(item1);
        option2 = ItemFixture.createOption2(item1);
        option3 = ItemFixture.createOption3(item1);
        optionRepository.save(option1);
        optionRepository.save(option2);
        optionRepository.save(option3);
    }

    @Test
    @WithCustomMockUser(loginId = "admin",authority = "admin")
    @DisplayName("상품 전체 조회 테스트")
    void itemFindAll() throws Exception {

        //given
        Item item = ItemFixture.createItem2(category1);
        itemRepository.save(item);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/items"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithCustomMockUser(loginId = "admin",authority = "admin")
    @DisplayName("상품 상세 조회 테스트")
    void itemDetailFind() throws Exception {

        //given
        Item item = ItemFixture.createItem2(category1);
        itemRepository.save(item);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/items/{itemId}",1))
                .andExpect(status().isOk());

        //then
        Assertions.assertThat(item.getItemName()).isEqualTo("덩크");
    }

    @Test
    @WithCustomMockUser(loginId = "admin",authority = "admin")
    @DisplayName("상품 등록")
    void itemCreate() throws Exception {

        //given
        ItemRequest itemRequest = ItemFixture.createItemRequest2();

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/items")
                        .content(objectMapper.writeValueAsString(itemRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());

        //then
        Assertions.assertThat(itemRepository.findById(2L).get().getItemName()).isEqualTo("삼바");

    }

    @Test
    @WithCustomMockUser(loginId = "admin",authority = "admin")
    @DisplayName("상품 수정")
    void itemUpdate() throws Exception {
        //given
        ItemUpdateRequest itemUpdateRequest = ItemFixture.createItemUpdateRequest();

        //when
        mockMvc.perform(MockMvcRequestBuilders.put("/admin/items/{itemId}",1)
                        .content(objectMapper.writeValueAsString(itemUpdateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        //then

        Assertions.assertThat(itemRepository.findById(1L).get().getPrice()).isEqualTo(230000);
        Assertions.assertThat(itemRepository.findById(1L).get().getExplain()).isEqualTo("신상품");
    }

    @Test
    @WithCustomMockUser(loginId = "admin",authority = "admin")
    @DisplayName("상품 삭제")
    void itemDelete() throws Exception {
        //given

        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/items/{itemId}",1))
                .andExpect(status().isNoContent());

        //then
        Assertions.assertThat(pointRepository.findAll().size()).isEqualTo(0);
    }

}
