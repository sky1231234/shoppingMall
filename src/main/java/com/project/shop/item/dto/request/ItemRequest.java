package com.project.shop.item.dto.request;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Review;

import javax.validation.constraints.NotBlank;
import java.util.List;

public record ItemRequest(
        @NotBlank String categoryName,
        @NotBlank String brandName,
        @NotBlank String itemName,
        @NotBlank String price,
        @NotBlank String explain,
        @NotBlank List<ItemImgEnrollRequest> itemImgEnrollRequestList,
        @NotBlank List<OptionEnrollRequest> optionEnrollRequestList
        ) {

        public Item toEntity(){
                return Item.builder()
                        .categoryName(categoryName)
                        .brandName(brandName)
                        .itemName(itemName)
                        .price(price)
                        .explain(explain)
                        .itemImgEnrollRequestList(itemImgEnrollRequestList)
                        .optionEnrollRequestList(optionEnrollRequestList)
                        .build();
        }
}
