package com.project.shop.item.dto.request;

import com.project.shop.item.domain.Item;

import javax.validation.constraints.NotBlank;
import java.util.List;


public record ItemUpdateRequest(
        @NotBlank long itemId,
        @NotBlank CategoryUpdateRequest categoryUpdateRequest,
        @NotBlank String itemName,
        @NotBlank int price,
        @NotBlank String explain,

        @NotBlank List<String> itemImgUpdateRequestList,
        @NotBlank List<OptionUpdateRequest> optionUpdateRequestList) {

    public Item toEntity(){

        return Item.builder()
                .itemName(this.itemName())
                .price(this.price())
                .explain(this.explain())
                .build();
    }
}
