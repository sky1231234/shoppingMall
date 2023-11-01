package com.project.shop.item.dto;

import com.project.shop.item.domain.Item;
import com.project.shop.user.domain.User;
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
    private String itemPrice;
    private String itemExplain;
    private List<ItemImgResponse> itemImgList;
    private String itemSize;
    private String itemColor;

        public static ItemResponse getResponse(Item item){
            return ItemResponse.builder()
                .categoryName()
                .brandName()
                .itemName(item.getItemName())
                .itemPrice(item.getPrice())
                .itemExplain(item.getExplain())
                .itemImgList(item.getPrice())
                .itemSize()
                .itemColor()
                .build();
    }
}