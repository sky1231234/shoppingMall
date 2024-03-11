package com.project.shop.item.dto.response;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import lombok.*;

@Getter
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
    private ItemImgResponse thumbnail;

    public static ItemListResponse of(Item item, ItemImgResponse mainItemImg){

        return new ItemListResponse(
                item.getItemId(),
                item.getCategory().getCategoryName(),
                item.getCategory().getBrandName(),
                item.getItemName(),
                item.getPrice(),
                mainItemImg
        );
    }
}