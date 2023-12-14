package com.project.shop.item.data;

import com.project.shop.item.domain.*;
import com.project.shop.item.dto.request.ItemRequest;

import java.time.LocalDateTime;

public class ItemData {

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
}
