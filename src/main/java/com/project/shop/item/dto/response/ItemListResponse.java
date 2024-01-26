package com.project.shop.item.dto.response;

import com.project.shop.item.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemListResponse {

//    {
//        "itemId": 1,
//        "categoryName": "카테고리명",
//        "brandName": "브랜드명",
//        "itemName": "상품명",
//        "itemPrice": 1000,
//        "thumbnail": {
//              "imgId": 1,
//              "url": "dd"
//          }
//    }

    private long itemId;
    private String categoryName;
    private String brandName;
    private String itemName;
    private int itemPrice;
    private Thumbnail thumbnail;

    @Builder
    @Getter
    public static class Thumbnail{
        private long imgId;
        private String url;
    }


}