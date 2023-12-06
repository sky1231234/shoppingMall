package com.project.shop.order.dto.request;

import com.project.shop.order.domain.Order;
import com.project.shop.order.domain.Pay;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public record OrderRequest(

//{
//              "orderTotalPrice" : "주문상품 총 가격",
//              "deliverFee" : "배송비",
//              "receiverName" : "받는사람이름",
//              "zipcode" :"우편번호",
//              "address" : "주소",
//              "addressDetail" : "상세주소",
//              "receiverPhoneNum" : "받는사람전화번호",
//              "addrMsg" : "배송메시지"
//
//              "usedPoint" : "사용포인트",
//              "payCompany" : "카드사",
//              "cardNum" : "카드일련번호",
//              "payPrice" : "결제금액"
//
//                "item" : [
//                    {
//                      "itemId" : "상품id",
//                      "itemCount" : "주문상품 수량",
//                      "itemPrice" : "상품 총 가격"
//                      "itemSize" : "주문상품 사이즈",
//                      "itemColor" : "주문상품 색상",
//                    }
//               ]
//}

        @NotNull int orderTotalPrice,
        @NotNull int deliverFee,
        @NotBlank String receiverName,
        @NotBlank String zipcode,
        @NotBlank String address,
        @NotBlank String addressDetail,
        @NotBlank String receiverPhoneNum,
        @NotBlank String addrMsg,

        @NotNull int usedPoint,
        @NotBlank String payCompany,
        @NotBlank String cardNum,
        @NotNull int payPrice,

        @NotNull List<OrderItemRequest> orderItemRequestList

        ) {

        public Order orderToEntity(){
            return Order.builder()
                    .price(this.orderTotalPrice())
                    .deliverFee(this.deliverFee( ))
                    .receiverName(this.receiverName())
                    .zipcode(this.zipcode())
                    .address(this.address())
                    .addrDetail(this.addressDetail())
                    .phoneNum(this.receiverPhoneNum())
                    .msg(this.addrMsg())
                    .build();
        }

        public Pay payToEntity(Order order){
            return Pay.builder()
                    .order(order)
                    .payCompany(this.payCompany())
                    .cardNum(this.cardNum())
                    .payPrice(this.payPrice())
                    .build();
        }



}
