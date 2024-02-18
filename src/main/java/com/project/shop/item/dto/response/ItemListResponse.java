package com.project.shop.item.dto.response;

import lombok.*;

@Getter
@Setter
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
    private int price;
    private Thumbnail thumbnail;

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Thumbnail{
        private long itemImgId;
        private String imgUrl;
    }

}