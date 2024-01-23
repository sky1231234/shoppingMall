package com.project.shop.order.Builder;

import com.project.shop.item.domain.Item;
import com.project.shop.member.domain.Address;
import com.project.shop.member.domain.AddressType;
import com.project.shop.member.domain.Member;
import com.project.shop.order.domain.*;
import com.project.shop.order.dto.request.OrderCancelRequest;
import com.project.shop.order.dto.request.OrderRequest;
import com.project.shop.order.dto.request.OrderUpdateRequest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class OrderBuilder {

    public static Order createOrder(Member member){
        return Order.builder()
                .member(member)
                .orderNum("20240120")
                .deliverFee(0)
                .point(1400)
                .price(10000)
                .orderType(OrderType.완료)
                .receiverName("받는사람")
                .zipcode("010101")
                .address("주소")
                .addrDetail("상세주소")
                .phoneNum("01011111111")
                .msg("배송 메시지")
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }

    public static OrderItem createOrderItem1(Item item, Order order){
        return OrderItem.builder()
                .item(item)
                .order(order)
                .itemOptionId(1)
                .totalQuantity(3)
                .totalPrice(12000)
                .itemPrice(4000)
                .orderItemType(OrderItemType.완료)
                .build();
    }

    public static OrderItem createOrderItem2(Item item, Order order){
        return OrderItem.builder()
                .item(item)
                .order(order)
                .itemOptionId(3)
                .totalQuantity(2)
                .totalPrice(14000)
                .itemPrice(7000)
                .orderItemType(OrderItemType.완료)
                .build();
    }

    public static Pay createPay(Order order){
        return Pay.builder()
                .order(order)
                .payCompany("농협")
                .cardNum("2790101010")
                .payPrice(26000)
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }

    public static OrderRequest createOrderRequest(Item item){

        return new OrderRequest(
                250000,
                0,
                "받는사람",
                "010101",
                "주소",
                "상세 주소",
                "01000000000",
                "집 앞에 놔주세요",
                1000,
                "국민",
                "00000001",
                250000,
                createOrderItemRequest(item)

        );
    }

    public static List<OrderRequest.OrderItemRequest> createOrderItemRequest(Item item) {

        return List.of(new OrderRequest.OrderItemRequest(
                        item.getItemId(),
                        3,
                        100000,
                        "240",
                        "빨강"
                ),
                new OrderRequest.OrderItemRequest(
                        item.getItemId(),
                        2,
                        150000,
                        "220",
                        "검정"
                )
        );

    }

    public static OrderUpdateRequest createOrderUpdateRequest(Order order, Item item){

        return new OrderUpdateRequest(
                390000,
                2500,
                "받는사람 수정",
                "020202",
                "주소 수정",
                "상세 주소 수정",
                "02000000000",
                "집 밖에 놔주세요",
                2000,
                "국민",
                "10000001",
                390000,
                createOrderUpdateItemRequest(order, item)

        );
    }

    public static List<OrderUpdateRequest.OrderItemRequest> createOrderUpdateItemRequest(Order order, Item item) {

        return List.of(new OrderUpdateRequest.OrderItemRequest(
                        order.getOrderId(),
                        item.getItemId(),
                        3,
                        30000,
                        "240",
                        "빨강"
                ),
                new OrderUpdateRequest.OrderItemRequest(
                        order.getOrderId(),
                        item.getItemId(),
                        2,
                        150000,
                        "220",
                        "검정"
                )
        );

    }

    public static OrderCancelRequest createOrderCancelRequest(){
        return new OrderCancelRequest(
                List.of(1L),
                "농협",
                "10102020",
                35000,
                "사이즈 미스"
        );
    }
}