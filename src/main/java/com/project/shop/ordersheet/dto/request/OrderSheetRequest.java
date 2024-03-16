package com.project.shop.ordersheet.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public record OrderSheetRequest (

//{
//              "receiverName" : "받는사람이름",
//              "zipcode" :"우편번호",
//              "address" : "주소",
//              "addressDetail" : "상세주소",
//              "receiverPhoneNum" : "받는사람 전화번호",
//              "addrMsg" : "배송메시지"

//                "item" : [
//                    {
//                      "itemId" : "상품id",
//                      "itemCount" : "주문상품 수량",
//                      "itemPrice" : "상품 가격"
//                      "itemSize" : "주문상품 사이즈",
//                      "itemColor" : "주문상품 색상",
//                    }
//               ]
//}

    @NotBlank String receiverName,
    @NotBlank String zipcode,
    @NotBlank String address,
    @NotBlank String addressDetail,
    @NotBlank String receiverPhoneNum,
    @NotBlank String addrMsg,

    @NotNull List<OrderItemRequest> orderItemRequestList

    )
    {

    }

