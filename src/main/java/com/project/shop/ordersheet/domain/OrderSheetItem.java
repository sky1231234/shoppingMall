package com.project.shop.ordersheet.domain;

import com.project.shop.item.domain.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Component
public class OrderSheetItem {

    private Item item;
    private long itemOptionId;
    private int orderQuantity;

    private final String ORDER_QUANTITY_IF_NOT_POSITIVE_MESSAGE =  "상품의 주문 수량은 1개 이상이여야한다.";

    private final int MIN_ORDER_QUANTITY = 1;

    public OrderSheetItem(
            Item item, long itemOptionId,
            int orderQuantity
     ){
        validation(orderQuantity);
        this.item = item;
        this.itemOptionId = itemOptionId;
        this.orderQuantity = orderQuantity;
    }

    public void validation(int orderQuantity){

        if (orderQuantity < MIN_ORDER_QUANTITY){
            throw new IllegalArgumentException(ORDER_QUANTITY_IF_NOT_POSITIVE_MESSAGE);
        }

    }

}

