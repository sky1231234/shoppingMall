package com.project.shop.user.dto.response;

import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.ItemImg;
import com.project.shop.user.domain.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {

//    {
//        "itemId" : 1,
//        "categoryName" : "카테고리명",
//        "brandName" : "브랜드명",
//        "itemName" : "상품명",
//        "itemSize" : "상품사이즈",
//        "itemColor" : "상품색상",
//        "cartCount" : 2,
//        "itemPrice" : 1000,
//        "thumbnail" : {
//              "imgId" : 1,
//              "url" : "dd"
//          },
//    }

    private long itemId; //상품id
    private String categoryName;
    private String brandName;
    private String itemName; //상품명
    private String itemSize; //상품 사이즈
    private String itemColor; //상품 색상
    private long count; //장바구니 수량
    private Thumbnail itemThumbnail; //상품 메인이미지

    @Builder
    public static class Thumbnail{
        private long imgId;
        private String url;
    }

}