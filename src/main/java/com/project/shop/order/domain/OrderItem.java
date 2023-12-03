package com.project.shop.order.domain;

import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
import com.project.shop.item.dto.request.ItemUpdateRequest;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "orderItem")
@Entity
@Getter
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderItemId")
    private long orderItemId;     //주문상품번호
    @ManyToOne
    @JoinColumn(name = "itemId")
    private Item item;     //상품 번호

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;     //주문번호

    @Column(name = "itemOptionId", nullable = false)
    private long itemOptionId;     //옵션번호

    @Column(name = "totalQuantity", nullable = false)
    private int totalQuantity;     //주문 총 수량
    @Column(name = "totalPrice", nullable = false)
    private int totalPrice;     //주문상품 총 가격
    @Column(name = "itemPrice", nullable = false)
    private int itemPrice;     //상품 가격
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType orderType;    //주문상태

    public OrderItem createOrderItem(){
        this.item = category;
        this.order = itemUpdateRequest.getItemName();
        return this;
    }

    public OrderItem cancelOrder(OrderType orderType){
        this.orderType = orderType;
        return this;
    }

}
