package com.project.shop.item.dto.response;

import com.project.shop.item.domain.Item;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponse {
    private String categoryName;
    private String brandName;
    private String itemName;
    private int itemPrice;
    private String itemExplain;
    private List<ItemImgResponse> itemImgList;
    private List<OptionResponse> optionResponseList;

        public static ItemResponse getResponse(Item item){
            return ItemResponse.builder()
                .categoryName(item.getCategory().getCategoryName())
                .brandName(item.getCategory().getBrandName())
                .itemName(item.getItemName())
                .itemPrice(item.getPrice())
                .itemExplain(item.getExplain())
                .itemImgList(ItemImgResponse.getResponse(item))
                .optionResponseList(OptionResponse.getResponse(item))
                .build();
    }
}