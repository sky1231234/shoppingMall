package com.project.shop.order.dto.request;

import com.project.shop.item.domain.Item;
import com.project.shop.order.domain.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
//
//}

        @NotNull List<Item> item,
        @NotBlank String cancelPayCompany,
        @NotBlank String cancelCardNum,
        @NotNull int cancelPayPrice,
        @NotBlank String cancelReason,
        @NotBlank PayCancelType payCancelType

) {

    public PayCancel toEntity(Order order){
        return PayCancel.builder()
                .order(order)
                .payCompany(this.getCancelPayCompany())
                .cardNum(this.getCancelCardNum())
                .cancelReason(this.getCancelReason())
                .payPrice(this.getCancelPayPrice())
                .payCancelType(this.getPayCancelType())
//                .usedPoint(this.getUsedPoint())
                .build();
    }



}
