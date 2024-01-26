package com.project.shop.item.dto.response;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.Review;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponse {

//    {
//        "itemId": 1,
//        "categoryName": "카테고리명",
//        "brandName": "브랜드명",
//        "itemName": "상품명",
//        "itemExplain": "상품설명",
//        "itemPrice": 1000,
//        "thumbnail": {
//              "imgId": 1,
//              "url": "dd"
//          },
//        "itemImg": [
//           {
//              "imgId": 1,
//              "url": "dd"
//           }
//         ],
//        "size": [
//            {
//              "optionId": 1,
//              "size": "사이즈"
//            }
//          ],
//        "color": [
//            {
//              "optionId": 1,
//              "color": "색상"
//              }
//          ]
//    }

    private long itemId;
    private String categoryName;
    private String brandName;
    private String itemName;
    private int itemPrice;
    private String itemExplain;
    private ItemImgResponse itemThumbnail;   //상품 대표 이미지

    private List<ItemImgResponse> itemImgResponseList;
    private List<OptionSize> optionSizeList;
    private List<OptionColor> optionColorList;

    @Builder
    @Getter
    public static class ItemImgResponse{
        private long imgId;
        private String url;
    }

    @Builder
    @Getter
    public static class OptionSize{
        private long optionId;
        private String size;
    }

    @Builder
    @Getter
    public static class OptionColor{
        private long optionId;
        private String color;
    }

}