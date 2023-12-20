package com.project.shop.order.dto.request;

import com.project.shop.order.domain.*;
import com.project.shop.user.domain.Point;
import com.project.shop.user.domain.PointType;
import com.project.shop.user.domain.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;


public record OrderCancelRequest(

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

        @NotNull List<Long> item,
        @NotBlank String cancelPayCompany,
        @NotBlank String cancelCardNum,
        @NotNull int cancelPayPrice,
        @NotBlank String cancelReason,
        @NotBlank String payCancelType

) {

    public PayCancel payCancelToEntity(Order order){
        return PayCancel.builder()
                .order(order)
                .orderNum(order.getOrderNum())
                .payCompany(this.cancelPayCompany())
                .cardNum(this.cancelCardNum())
                .cancelReason(this.cancelReason())
                .payPrice(this.cancelPayPrice())
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }

    public Point pointToEntity(User user, int point, PointType pointType){
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
