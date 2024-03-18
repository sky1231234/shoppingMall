package com.project.shop.ordersheet.domain;

import com.project.shop.item.domain.Item;
import com.project.shop.member.domain.Address;
import com.project.shop.member.domain.Member;
import com.project.shop.member.exception.MemberException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderSheetTest {

    @Mock
    Address address;
    @Mock
    List<OrderSheetItem> orderSheetItems;
    @Mock
    Item item;

    @Test
    public void createOrderSheet(){

        List<OrderSheetItem> mockOrderItems = orderSheetItems;
        int usedPoint = 500;
        int itemSumPrice = 148000;
        int deliverFee = 0;
        int finalPrice =  147500;
        Address mockAddress = address;
        LocalDateTime dateTime = LocalDateTime.now();

        OrderSheet orderSheet = new OrderSheet(mockOrderItems, usedPoint, itemSumPrice, finalPrice, deliverFee, mockAddress, dateTime);

        assertEquals(mockOrderItems, orderSheet.getOrderSheetItems());
        assertEquals(usedPoint, orderSheet.getUsedPoint());
        assertEquals(itemSumPrice, orderSheet.getItemSumPrice());
        assertEquals(deliverFee, orderSheet.getDeliverFee());
        assertEquals(finalPrice, orderSheet.getFinalPrice());
        assertEquals(mockAddress, orderSheet.getAddress());
        assertEquals(dateTime, orderSheet.getInsertDate());

    }

    @Test
    public void sumAllPriceAndAmount(){
        OrderSheet orderSheet = new OrderSheet();

        List<OrderSheetItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderSheetItem(item, 1, 2,24000)); // 상품 가격 10000원, 수량 2개
        orderItems.add(new OrderSheetItem(item, 2, 5,20000)); // 상품 가격 20000원, 수량 1개

        orderSheet.addOrderSheetItem(orderItems);

        int itemSumPrice = orderSheet.sumAllPriceAndAmount(orderItems);

        assertThat(itemSumPrice).isEqualTo(148000);

    }
    @Test
    public void applyPoints(){

        //given
        OrderSheet orderSheet = new OrderSheet();

        int point = 5000;
        int itemSumPrice = 148000;

        //when
        int calculateSumPriceAfterPoints = orderSheet.applyPoints(itemSumPrice, point);

        //then
        assertThat(calculateSumPriceAfterPoints).isEqualTo(143000);

    }

    @Test
    public void applyPoints_WithNoPoint(){

        //given
        OrderSheet orderSheet = new OrderSheet();

        int point = 0;
        int itemSumPrice = 148000;

        //when
        int calculateSumPriceAfterPoints = orderSheet.applyPoints(itemSumPrice, point);

        //then
        assertThat(calculateSumPriceAfterPoints).isEqualTo(148000);

    }

    @Test
    public void applyPoints_WithManyPoints(){

        //given
        OrderSheet orderSheet = new OrderSheet();

        int point = 150000;
        int itemSumPrice = 148000;

        //when
        //then
        assertThrows(RuntimeException.class,
                () -> orderSheet.applyPoints(
                        itemSumPrice,
                        point));

    }

    @Test
    public void calculateFinalPrice(){

        //given
        OrderSheet orderSheet = new OrderSheet();

        int point = 5000;
        int itemSumPrice = 148000;
        int totalPriceAfterPoints = orderSheet.applyPoints(itemSumPrice, point);
        int deliverFee = 2500;

        int finalPrice = orderSheet.calculateTotalPriceAfterPoints(totalPriceAfterPoints, deliverFee);

        assertThat(finalPrice).isEqualTo(145500);
    }
}
