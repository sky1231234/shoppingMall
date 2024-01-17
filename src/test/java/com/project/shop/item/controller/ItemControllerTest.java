package com.project.shop.item.controller;

import com.project.shop.item.Builder.CategoryBuilder;
import com.project.shop.item.Builder.ItemBuilder;
import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Option;
import com.project.shop.item.dto.request.ItemRequest;
import com.project.shop.item.repository.CategoryRepository;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.OptionRepository;
import com.project.shop.item.service.ItemService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    CategoryRepository categoryRepository;
    static Item item1;
    static Item item2;

    @BeforeEach
    void beforeEach(){
        Category category = CategoryBuilder.createCategory1();
        categoryRepository.save(category);

        item1 = ItemBuilder.createItem1(category);
        item2 = ItemBuilder.createItem2(category);

        itemRepository.save(item1);
        itemRepository.save(item2);
    }
    @Test
    @DisplayName("상품 전체 조회 테스트")
    void itemFindAll() throws Exception {
        //given
        //when

        //then
        mvc.perform(MockMvcRequestBuilders.get("/items"))
                .andExpect(status().isOk()); //200 상태


        //then
        Assertions.assertThat(itemRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("상품 상세 조회 테스트")
    void itemFindDetail() throws Exception {
        //given
        //when

        //then
        mvc.perform(MockMvcRequestBuilders.get("/items"))
                .andExpect(status().isOk()); //200 상태


        //then
        Assertions.assertThat(itemRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("상품 등록")
    void itemCreate() throws Exception {
//        ItemRequest itemRequest = getItemRequest();
//
//        var result = itemService.create(itemRequest);
//
//        Item item = itemRepository.findById(result)
//                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ITEM"));
//        List<Option> findOption = optionRepository.findByItem(item);
//
//        Assertions.assertThat(result).isEqualTo(3);
//        Assertions.assertThat(findOption.size()).isEqualTo(3);
//        Assertions.assertThat(findOption.get(0).getColor()).isEqualTo("검정");

    }

}
