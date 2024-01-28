package com.project.shop.order.dto.request;

import com.project.shop.order.domain.*;
import com.project.shop.member.domain.Point;
import com.project.shop.member.domain.PointType;
import com.project.shop.member.domain.Member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
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
        @NotBlank String cancelReason

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

    public Point pointToEntity(Member member, int point, PointType pointType){
        return Point.builder()
                .member(member)
                .point(point)
                .deadlineDate(LocalDate.now().plusWeeks(1))
                .pointType(pointType)
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }

}
