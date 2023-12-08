package com.project.shop.item.service;

import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
import com.project.shop.item.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class ItemServiceTest {

    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;

    Item item1 = null;
    Item item2 = null;

    @BeforeEach
    public void before(){
        LocalDateTime now = LocalDateTime.now();

        Category category = Category.builder()
                .categoryName("운동화")
                .brandName("나이키")
                .insertDate(now)
                .updateDate(now)
                .build();

        item1 = Item.builder()
                .category(category)
                .itemName("조던")
                .price(1000000)
                .explain("인기많음")
                .insertDate(now)
                .updateDate(now)
                .build();

        item2 = Item.builder()
                .category(category)
                .itemName("덩크")
                .price(5000000)
                .explain("인기없음")
                .insertDate(now)
                .updateDate(now)
                .build();

        itemRepository.save(item1);
        itemRepository.save(item2);
    }

    @Test
    @DisplayName("상품 전체 조회")
    void itemFindAll(){
        var result = itemService.itemFindAll();

        System.out.println(result);
        Assertions.assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("상품 상세 조회")
    void itemFindDetail(){
        var result = itemService.itemDetailFind(item1.getItemId());

        Assertions.assertThat(result).isNotNull();
    }
}
