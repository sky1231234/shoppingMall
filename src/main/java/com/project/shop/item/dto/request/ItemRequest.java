package com.project.shop.item.dto.request;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.ItemImgType;
import com.project.shop.item.domain.Option;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public record ItemRequest(
        @NotBlank CategoryRequest categoryRequest,
        @NotBlank String itemName,
        @NotNull int price,
        @NotBlank String explain,

        //service에서 처리
        @NotBlank List<ImgRequest> itemImgRequestList,
        @NotBlank List<OptionRequest> optionRequestList
        ) {

        public Item toEntity(){
                return Item.builder()
                        .category(categoryRequest.toEntity())
                        .itemName(this.itemName())
                        .price(this.price())
                        .explain(this.explain())
                        .insertDate(LocalDateTime.now())
                        .updateDate(LocalDateTime.now())
                        .build();
        }

        //img
        public record ImgRequest(
                @NotBlank ItemImgType mainImg,
                @NotBlank String url
        ) {}

        //option
        public record OptionRequest(
                @NotBlank String size,
                @NotBlank String color
        ) {}
}
