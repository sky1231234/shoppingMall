package com.project.shop.ordersheet.domain;

import com.project.shop.item.domain.Item;
import com.project.shop.order.domain.Order;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderSheetItem {

    private Item item;     //상품
    private long itemOptionId;     //옵션번호
    private int orderQuantity;     //주문 수량

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

