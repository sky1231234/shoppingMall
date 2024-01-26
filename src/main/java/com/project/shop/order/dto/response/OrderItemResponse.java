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
public class OrderItemResponse {

//    {
//        "orderId" : 1,
//        "orderDate" :"주문일",
//        "orderTotalPrice" : "주문상품 총 가격",
//        "orderState" : "주문상태",
//        "deliverFee" : "배송비",
//        "item" : [
//              {
//                  "itemId" : 1,
//                  "itemName" : "상품명",
//                  "itemThumbnail" : {
//                      "imgId" : 1,
//                      "url" : "경로"
//                  },
//                  "itemSize" : "주문상품 사이즈",
//                  "itemColor" : "주문상품 색상",
//                  "itemCount" : "주문상품 수량",
//                  "itemPrice" : "상품 총 가격"
//                  ]
//              }
//    }

    private long orderId;
    private LocalDateTime orderDate;
    private int orderTotalPrice;
    private OrderType orderState;
    private int deliverFee;

    private List<OrderItem> orderItem;

    @Builder
    public static class OrderItem {
        private long itemId;
        private String itemName;
        private Thumbnail itemThumbnail;
        private int itemCount;
        private String itemSize;
        private String itemColor;
        private int itemPrice;
    }

    @Builder
    public static class Thumbnail {
        private long imgId;
        private String url;
    }

}