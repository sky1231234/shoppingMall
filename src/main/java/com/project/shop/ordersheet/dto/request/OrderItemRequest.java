package com.project.shop.ordersheet.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record OrderItemRequest(

    //            {
//              "itemId" : "상품id",
//              "itemCount" : "주문상품 수량",
//              "itemPrice" : "상품 가격"
//              "itemSize" : "주문상품 사이즈",
//              "itemColor" : "주문상품 색상",
//              }

    @NotNull long itemId,
    @NotNull int itemCount,
    @NotNull int itemPrice,
    @NotBlank String itemSize,
    @NotBlank String itemColor
    )
    {

    }