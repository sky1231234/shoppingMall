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
    private int itemPrice;     //상품 가격
    private int orderQuantity;     //주문 수량

    @Builder
    public OrderSheetItem(
            Item item, long itemOptionId,
            int itemPrice ,int orderQuantity
     ){
        validation(item,itemOptionId,itemPrice,orderQuantity);
        this.item = item;
        this.itemOptionId = itemOptionId;
        this.itemPrice = itemPrice;
        this.orderQuantity = orderQuantity;
    }

    public void validation(Item item, long itemOptionId,
                            int orderQuantity,int itemPrice){
//        item.checkItemExist(item);

        validateItemPrice(itemPrice);
        validateOrderQuantity(orderQuantity);

    }

    public void validateItemPrice(int itemPrice){
        if(itemPrice <= 0){
            throw new IllegalArgumentException("상품의 가격은 0원 초과여야한다.");
        }
    }

    public void validateOrderQuantity(int orderQuantity){
        if (orderQuantity < 1){
            throw new IllegalArgumentException("상품의 주문 수량은 1개 이상이여야한다.");
        }
    }
}

