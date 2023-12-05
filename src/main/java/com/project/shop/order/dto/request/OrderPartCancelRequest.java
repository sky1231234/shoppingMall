package com.project.shop.order.dto.request;

import com.project.shop.order.domain.*;
import com.project.shop.user.domain.Point;
import com.project.shop.user.domain.PointType;
import com.project.shop.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public record OrderPartCancelRequest(

//{
//      "item" : [
//            {
//                "itemId" : 1
//            }
//      ],
//      "cancelPayCompany" : "취소 카드사",
//      "cancelCardNum" : "취소 카드 일련번호",
//      "cancelPayPrice" : 500,
//      "cancelReason" : "취소 사유"
//      "payCancelType" : "부분취소"
//      "usedPoint" : 500
//}

        @NotNull List<ItemId> item,
        @NotBlank String cancelPayCompany,
        @NotBlank String cancelCardNum,
        @NotNull int cancelPayPrice,
        @NotBlank String cancelReason,
        @NotBlank String payCancelType,
        @NotNull int usedPoint



) {
    public record ItemId(
            @NotNull long itemId
    ) {}

    public Order cancelToEntity(Order order, OrderType orderType){
        return Order.builder()
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
                .build();
    }

    public Point pointToEntity(User user, int point, PointType pointType){
        return Point.builder()
                .user(user)
                .point(point)
                .deadlineDate(LocalDateTime.now().plusWeeks(1))
                .pointType(pointType)
                .build();
    }

}
