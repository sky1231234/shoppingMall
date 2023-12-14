package com.project.shop.order.dto.response;

import com.project.shop.order.domain.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderUserResponse {

//    {
//          "userId" : 1,
//          "id" : "아이디",
//          "order" : [
//              {
//                  "orderId" : 1,
//                  "orderNum" : "주문 완료 번호",
//                  "orderDate" :"주문일",
//                  "orderTotalPrice" : "주문상품 총 가격",
//                  "orderState" : "주문상태",
//                  "deliverFee" : "배송비",
//              }
//            ]

//    }

    private long userId;
    private String id;

    private List<Order> order;

    @Builder
    @Getter
    public static class Order {
        private long orderId;
        private String orderNum;
        private LocalDateTime orderDate;
        private int orderTotalPrice;
        private OrderType orderState;
        private int deliverFee;
    }

}