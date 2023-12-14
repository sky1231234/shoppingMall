package com.project.shop.order.dto.request;

import com.project.shop.order.domain.*;
import com.project.shop.user.domain.Point;
import com.project.shop.user.domain.PointType;
import com.project.shop.user.domain.User;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record OrderCancelRequest(

//{
//      "userId" : 1,
//      "orderId" : 1,
//
//      "cancelPayCompany" : "취소 카드사",
//      "cancelCardNum" : "취소소카드일련번호",
//      "cancelPayPrice" : 500,
//      "cancelReason" : "취소사유"
//      "payCancelType" : "취소"
//      "usedPoint" : 500
//}

        @NotBlank String cancelPayCompany,
        @NotBlank String cancelCardNum,
        @NotNull int cancelPayPrice,
        @NotBlank String cancelReason,
        @NotBlank String payCancelType,
        @NotNull int usedPoint

        ) {

    public Order cancelToEntity(User user, Order order, OrderType orderType){
        return Order.builder()
                .users(user)
                .orderNum(order.getOrderNum())
                .deliverFee(order.getDeliverFee())
                .point(order.getPoint())
                .price(order.getPrice())
                .orderType(orderType)
                .receiverName(order.getReceiverName())
                .zipcode(order.getZipcode())
                .address(order.getAddress())
                .addrDetail(order.getAddrDetail())
                .phoneNum(order.getPhoneNum())
                .msg(order.getMsg())
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }

        public PayCancel payCancelToEntity(Order order, PayCancelType payCancelType){
            return PayCancel.builder()
                    .order(order)
                    .orderNum(order.getOrderNum())
                    .payCompany(this.cancelPayCompany())
                    .cardNum(this.cancelCardNum())
                    .cancelReason(this.cancelReason())
                    .payPrice(this.cancelPayPrice())
                    .payCancelType(payCancelType)
                    .insertDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();
        }

        public Point pointToEntity(User user, int point,PointType pointType){
            return Point.builder()
                    .users(user)
                    .point(point)
                    .deadlineDate(LocalDateTime.now().plusWeeks(1))
                    .pointType(pointType)
                    .insertDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();
        }


}
