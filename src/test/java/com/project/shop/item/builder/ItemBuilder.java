package com.project.shop.item.builder;

import com.project.shop.item.domain.*;
import com.project.shop.item.dto.request.ItemRequest;
import com.project.shop.item.dto.request.ItemUpdateRequest;


import java.time.LocalDateTime;
import java.util.List;

public class ItemBuilder {

    static LocalDateTime now = LocalDateTime.now();

    public static Item createItem1(Category category){

        return Item.builder()
                .category(category)
                .itemName("조던")
                .price(1000000)
                .explain("인기 많음")
                .insertDate(now)
                .updateDate(now)
                .build();
    }

    public static Item createItem2(Category category){

        return Item.builder()
                .category(category)
                .itemName("덩크")
                .price(5000000)
                .explain("인기 없음")
                .insertDate(now)
                .updateDate(now)
                .build();
    }

    public static ItemImg createImg1(Item item){

        return ItemImg.builder()
                .item(item)
                .imgUrl("itemImg1")
                .itemImgType(ItemImgType.Y)
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }

    public static ItemImg createImg2(Item item){

        return ItemImg.builder()
                .item(item)
                .imgUrl("itemImg2")
                .itemImgType(ItemImgType.N)
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }

    public static ItemImg createImg3(Item item){

        return ItemImg.builder()
                .item(item)
                .imgUrl("itemImg3")
                .itemImgType(ItemImgType.Y)
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }

    public static Option createOption1(Item item){

        return Option.builder()
                .item(item)
                .size("220")
                .color("검정")
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }

    public static Option createOption2(Item item){

        return Option.builder()
                .item(item)
                .size("230")
                .color("파랑")
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }

    public static Option createOption3(Item item){

        return Option.builder()
                .item(item)
                .size("240")
                .color("빨강")
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }

    public static ItemRequest createItemRequest1(){
        return new ItemRequest(
                CategoryBuilder.createCategoryRequest2(),
                "조던1",
                5000,
                "재고 없음",
                List.of(createItemImg1(), createItemImg2()),
                List.of(createOption1(), createOption2(),createOption3())
        );
    }

    public static ItemRequest createItemRequest2(){

        return new ItemRequest(
                CategoryBuilder.createCategoryRequest2(),
                "삼바",
                109000,
                "품절이 잦습니다.",
                List.of(createItemImg1()),
                List.of(createOption1())
                );
    }

    public static ItemUpdateRequest createItemUpdateRequest(){

        return new ItemUpdateRequest(
                CategoryBuilder.createCategoryUpdateRequest(),
                "물",
                230000,
                "신상품",
                List.of(),
                List.of(createOptionUpdateRequest1(),createOptionUpdateRequest2())
        );
    }



    public static ItemRequest.ImgRequest createItemImg1(){

        return new ItemRequest.ImgRequest(ItemImgType.Y,"itemImg1");
    }

    public static ItemRequest.ImgRequest createItemImg2(){

        return new ItemRequest.ImgRequest(ItemImgType.N,"itemImg2");
    }

    public static ItemRequest.OptionRequest createOption1(){

        return new ItemRequest.OptionRequest("220","검정");
    }

    public static ItemRequest.OptionRequest createOption2(){

        return new ItemRequest.OptionRequest("230","파랑");
    }

    public static ItemRequest.OptionRequest createOption3(){

        return new ItemRequest.OptionRequest("240","빨강");
    }

    public static ItemUpdateRequest.OptionUpdateRequest createOptionUpdateRequest1(){

        return new ItemUpdateRequest.OptionUpdateRequest(
                "240","빨강");
    }

    public static ItemUpdateRequest.OptionUpdateRequest createOptionUpdateRequest2(){

        return new ItemUpdateRequest.OptionUpdateRequest(
                "220","검정");
    }



}
