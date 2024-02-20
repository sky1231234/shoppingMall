package com.project.shop.order.dto.response;

import com.project.shop.order.domain.OrderType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailResponse {

//    {
//        "orderId" : 1,
//        "orderDate" :"주문일",
//        "orderTotalPrice" : "주문상품 총 가격",
//        "orderState" : "주문상태",
//        "deliverFee" : "배송비",
//
//        "receiverName" : "받는사람이름",
//        "zipcode" :"우편번호",
//        "address" : "주소",
//        "addressDetail" : "상세주소",
//        "receiverPhoneNum" : "받는사람전화번호",
//
//         "item" : [
//              {
//                  "itmeId" : "상품id",
//                  "itemName" : "상품명",
//                  "itemThumbnail" : {
//                      "imgId" : 1,
//                      "url" : "경로"
//                      },
//                  "itemCount" : "주문상품 수량",
//                  "itemPrice" : "상품 총 가격"
//                  "itemSize" : "주문상품 사이즈",
//                  "itemColor" : "주문상품 색상",
//              }
//        ],
//        "payId" : "결제번호",
//        "usedPoint" : "사용포인트",
//        "payCompany" : "카드사",
//        "cardNum" : "카드일련번호",
//        "payPrice" : "결제금액"
//    }

    private long orderId;
    private LocalDateTime orderDate;
    private int orderTotalPrice;
    private OrderType orderState;
    private int deliverFee;

    private String receiverName;
    private String zipcode;
    private String address;
    private String addressDetail;
    private String receiverPhoneNum;

    private List<OrderItem> orderItem;
    private Pay pay;

    @Builder
    @Getter
    public static class OrderItem {
        private long itemId;
        private String itemName;
        private Thumbnail itemThumbnail;
        private int itemCount;
        private int itemPrice;
        private String itemSize;
        private String itemColor;
    }

    @Builder
    @Getter
    public static class Thumbnail {
        private long imgId;
        private String url;
    }

    @Builder
    @Getter
    public static class Pay {
        private long payId;
        private int usedPoint;
        private String payCompany;
        private String cardNum;
        private int payPrice;
    }

}