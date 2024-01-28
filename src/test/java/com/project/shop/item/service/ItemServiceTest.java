package com.project.shop.item.service;

import com.project.shop.common.service.ServiceCommon;
import com.project.shop.item.builder.CategoryBuilder;
import com.project.shop.item.builder.ItemBuilder;
import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Option;
import com.project.shop.item.dto.request.ItemRequest;
import com.project.shop.item.repository.CategoryRepository;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.OptionRepository;
import com.project.shop.member.builder.MemberBuilder;
import com.project.shop.member.domain.Authority;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ItemServiceTest extends ServiceCommon {

    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OptionRepository optionRepository;
    @Autowired
    CategoryRepository categoryRepository;
    static Item item1;
    static Item item2;

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

        Category category = CategoryBuilder.createCategory1();
        categoryRepository.save(category);

        item1 = ItemBuilder.createItem1(category);
        item2 = ItemBuilder.createItem2(category);

        itemRepository.save(item1);
        itemRepository.save(item2);
    }

    @Test
    @DisplayName("상품 전체 조회")
    void itemFindAll(){
        //when
        var result = itemService.itemFindAll();

        //then
        Assertions.assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("상품 상세 조회")
    void itemFindDetail(){
        var result = itemService.itemDetailFind(item1.getItemId());

        Assertions.assertThat(result.getItemName()).isEqualTo("조던");
        Assertions.assertThat(result.getItemExplain()).isEqualTo("인기 많음");
    }

    @Test
    @DisplayName("상품 등록")
    void itemCreate(){

        //given
        ItemRequest itemRequest = ItemBuilder.createItemRequest1();

        //when
        var result = itemService.create(adminId,itemRequest);

        Item item = itemRepository.findById(result)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ITEM"));
        List<Option> findOption = optionRepository.findByItem(item);

        Assertions.assertThat(result).isEqualTo(3);
        Assertions.assertThat(findOption.size()).isEqualTo(3);
        Assertions.assertThat(findOption.get(0).getColor()).isEqualTo("검정");

    }


    //상품 수정, 상품 삭제
}
