package com.project.shop.ordersheet.domain;

import com.project.shop.item.domain.Item;
import com.project.shop.order.domain.Order;
import lombok.*;

import javax.persistence.*;

@Table(name = "orderSheetItem")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderSheetItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long orderSheetItemId;     //주문상품번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderSheetId")
    private OrderSheet orderSheet;     //주문 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId")
    private Item item;     //상품 번호

    @Column(name = "itemOptionId", nullable = false)
    private long itemOptionId;     //옵션번호

    @Column(name = "totalQuantity", nullable = false)
    private int orderQuantity;     //주문 수량
    @Column(name = "itemPrice", nullable = false)
    private int itemPrice;     //상품 가격

    @Builder
    public OrderSheetItem(
            OrderSheet orderSheet, Item item, long itemOptionId,
                     int orderQuantity,int itemPrice
     ){
        this.orderSheet = orderSheet;
        this.item = item;
        this.itemOptionId = itemOptionId;
        this.orderQuantity = orderQuantity;
        this.itemPrice = itemPrice;
    }

}

