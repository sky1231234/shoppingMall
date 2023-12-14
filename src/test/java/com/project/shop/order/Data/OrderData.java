package com.project.shop.order.Data;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Option;
import com.project.shop.order.domain.Order;
import com.project.shop.order.domain.OrderItem;
import com.project.shop.order.domain.OrderType;
import com.project.shop.order.dto.request.OrderRequest;
import com.project.shop.user.domain.User;

import java.time.LocalDateTime;

public class OrderData {
    static LocalDateTime now = LocalDateTime.now();

    public static Order createOrder1(User user,String orderNum, OrderType orderType){

        return Order.builder()

                .build();
    }

    public static User createUser2(){

        return User.builder()
                .loginId("boot")
                .password("password")
                .name("부트")
                .address("부산")
                .addrDetail("광안리")
                .phoneNum("5555555")
                .insertDate(now)
                .updateDate(now)
                .build();
    }


}
