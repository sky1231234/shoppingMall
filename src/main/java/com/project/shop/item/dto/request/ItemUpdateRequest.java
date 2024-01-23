package com.project.shop.item.dto.request;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImgType;

import javax.validation.constraints.NotBlank;
import java.util.List;


public record ItemUpdateRequest(
        @NotBlank CategoryUpdateRequest categoryUpdateRequest,
        @NotBlank String itemName,
        @NotBlank int price,
        @NotBlank String explain,

        @NotBlank List<ImgUpdateRequest> itemImgUpdateRequestList,
        @NotBlank List<OptionUpdateRequest> optionUpdateRequestList) {

    public Item toEntity(){

        return Item.builder()
                .itemName(this.itemName())
                .price(this.price())
                .explain(this.explain())
                .build();
    }

    //img
    public record ImgUpdateRequest(
            @NotBlank ItemImgType mainImg,
            @NotBlank String url
    ) {}

    //option
    public record OptionUpdateRequest(
            @NotBlank String size,
            @NotBlank String color
    ) {}
}
