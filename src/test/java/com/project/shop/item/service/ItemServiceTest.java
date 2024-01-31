package com.project.shop.item.service;

import com.project.shop.common.service.ServiceCommon;
import com.project.shop.item.builder.CategoryBuilder;
import com.project.shop.item.builder.ItemBuilder;
import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.Option;
import com.project.shop.item.dto.request.ItemRequest;
import com.project.shop.item.dto.request.ItemUpdateRequest;
import com.project.shop.item.dto.response.ItemListResponse;
import com.project.shop.item.repository.CategoryRepository;
import com.project.shop.item.repository.ItemImgRepository;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.OptionRepository;
import com.project.shop.member.builder.MemberBuilder;
import com.project.shop.member.domain.Authority;
import com.project.shop.member.repository.PointRepository;
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

        //category
        category1 = CategoryBuilder.createCategory1();
        category2 = CategoryBuilder.createCategory2();
        category3 = CategoryBuilder.createCategory4();

        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        //item
        item1 =  ItemBuilder.createItem1(category1);
        itemRepository.save(item1);

        itemImg1 = ItemBuilder.createImg1(item1);
        itemImgRepository.save(itemImg1);

        option1 = ItemBuilder.createOption1(item1);
        option2 = ItemBuilder.createOption2(item1);
        option3 = ItemBuilder.createOption3(item1);
        optionRepository.save(option1);
        optionRepository.save(option2);
        optionRepository.save(option3);
    }

    @Test
    @DisplayName("상품 전체 조회")
    void itemFindAll(){

        //given

        //when
        List<ItemListResponse> itemListResponses = itemService.itemFindAll(loginId);

        //then

        Assertions.assertThat(itemListResponses.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("상품 상세 조회")
    void itemFindDetail(){

        //given

        //when
        var result = itemService.itemDetailFind(loginId, item1.getItemId());

        //then
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

    @Test
    @DisplayName("상품 수정")
    void itemUpdate(){

        //given
        ItemUpdateRequest itemUpdateRequest = ItemBuilder.createItemUpdateRequest();
        long itemId = 1;

        //when
        itemService.update(adminId,itemId, itemUpdateRequest);

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ITEM_TEST"));
        List<Option> findOption = optionRepository.findByItem(item);

        Assertions.assertThat(item.getItemName()).isEqualTo("물");
        Assertions.assertThat(findOption.size()).isEqualTo(2);
        Assertions.assertThat(findOption.get(1).getSize()).isEqualTo("220");

    }

    @Test
    @DisplayName("상품 삭제")
    void itemDelete(){

        //given
        long itemId = 1;

        //when
        itemService.delete(adminId,itemId);

        Assertions.assertThat(itemRepository.findAll().size()).isEqualTo(0);
        Assertions.assertThat(optionRepository.findAll().size()).isEqualTo(0);
    }

}
