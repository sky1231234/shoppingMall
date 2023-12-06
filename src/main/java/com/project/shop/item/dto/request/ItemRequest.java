package com.project.shop.item.dto.request;

import com.project.shop.item.domain.Item;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public record ItemRequest(
        @NotBlank CategoryRequest categoryRequest,
        @NotBlank String itemName,
        @NotNull int price,
        @NotBlank String explain,

        //service에서 처리
        @NotBlank List<ItemImgRequest> itemImgRequestList,
        @NotBlank List<OptionRequest> optionRequestList
        ) {

        //itemRequest -> item
        public Item toEntity(){
                return Item.builder()
                        .itemName(this.itemName())
                        .price(this.price())
                        .explain(this.explain())
                        .build();
        }
}
