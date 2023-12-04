package com.project.shop.item.service;

import com.project.shop.item.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ItemServiceTest {

    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    @BeforeAll
    public void start(){

    }

    @Test
    @DisplayName("아이템 조회")
    public void item(){
        var result = itemService.itemFindAll();

        Assertions.assertThat(result).isNotEmpty();
    }
}
