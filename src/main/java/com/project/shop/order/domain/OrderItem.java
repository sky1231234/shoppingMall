package com.project.shop.order.domain;

import com.project.shop.item.domain.Item;
import com.project.shop.order.dto.request.OrderRequest;
import com.project.shop.order.dto.request.OrderUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "orderItem")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long orderItemId;     //주문상품번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId")
    private Item item;     //상품 번호

    @ManyToOne(fetch = FetchType.LAZY)
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
    private OrderItemType orderItemType;    //주문상태

    public OrderItem cancelOrderItem(OrderItemType orderItemType){
        this.orderItemType = orderItemType;
        return this;
    }
}
