package com.project.shop.item.service;

import com.project.shop.item.data.CategoryData;
import com.project.shop.item.data.ItemData;
import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImgType;
import com.project.shop.item.domain.Option;
import com.project.shop.item.dto.request.CategoryRequest;
import com.project.shop.item.dto.request.ItemRequest;
import com.project.shop.item.repository.CategoryRepository;
import com.project.shop.item.repository.ItemImgRepository;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.OptionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class ItemServiceTest {

    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OptionRepository optionRepository;
    @Autowired
    ItemImgRepository itemImgRepository;
    @Autowired
    CategoryRepository categoryRepository;
    static Item item1;
    static Item item2;

    @BeforeEach
    public void before(){

        Category category = CategoryData.createCategory1();
        categoryRepository.save(category);

        item1 = ItemData.createItem1(category);
        item2 = ItemData.createItem2(category);

        itemRepository.save(item1);
        itemRepository.save(item2);
    }

    @Test
    @DisplayName("상품 전체 조회")
    void itemFindAll(){
        var result = itemService.itemFindAll();
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
        ItemRequest itemRequest = getItemRequest();

        var result = itemService.create(itemRequest);

        Item item = itemRepository.findById(result)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ITEM"));
        List<Option> findOption = optionRepository.findByItem(item);

        Assertions.assertThat(result).isEqualTo(3);
        Assertions.assertThat(findOption.size()).isEqualTo(3);
        Assertions.assertThat(findOption.get(0).getColor()).isEqualTo("검정");

    }

    private ItemRequest getItemRequest() {

        CategoryRequest categoryRequest = CategoryData.createCategoryRequest1();

//        final List<ItemRequest.ImgRequest> itemImg = List.of(
//                new ItemRequest.ImgRequest(ItemImgType.Y,"itemImg1"),
//                new ItemRequest.ImgRequest(ItemImgType.N,"itemImg2"));
        List<ItemRequest.ImgRequest> itemImg = List.of(
                ItemData.createItemImg1(),
                ItemData.createItemImg2());

        List<ItemRequest.OptionRequest> option = List.of(
                ItemData.createOption1(),
                ItemData.createOption2(),
                ItemData.createOption3());

        return new ItemRequest(
                categoryRequest,
                "조던1",
                5000,
                "재고 없음",
                itemImg,
                option);
    }
}
