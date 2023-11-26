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
    private long itemId; //상품id
    private String itemName; //상품명
    private ItemImg itemThumbnail; //상품 메인이미지
    private String itemSize; //상품 사이즈
    private String itemColor; //상품 색상
    private long count; //장바구니 수량


    public static CartResponse fromEntity(Cart cart){

        return CartResponse.builder()
                .itemId(cart.getItem().getItemId())
                .itemName(cart.getItem().getItemName())
//                .itemThumbnail(cart.getItem().getItemImgList())
                .itemSize(cart.getOption().getSize())
                .itemColor(cart.getOption().getColor())
                .count(cart.getCount())
                .build();
    }
}