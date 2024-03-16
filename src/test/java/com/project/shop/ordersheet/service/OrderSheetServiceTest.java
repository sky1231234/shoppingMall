package com.project.shop.ordersheet.service;

import com.project.shop.member.domain.Point;
import com.project.shop.member.service.PointService;
import com.project.shop.ordersheet.domain.OrderSheet;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderSheetServiceTest {

    @Mock
    private PointService pointService;

    @Mock
    private Point point;

    @InjectMocks
    private OrderSheet orderSheet;

    @Order(1)
    @Test
    void calculateAmountAndPrice_NoPoint_NotUsePoint(){

        //given
        long userId = 1L;
        int usingPoint = 0;

        when(pointService.calculatePointByUserId(userId)).thenReturn(0);
        when(pointService.checkAvailablePoint(pointService.calculatePointByUserId(userId),usingPoint)).thenReturn(usingPoint);


    }

    void calculateAmountAndPrice_NoPoint_UsePoint() {

        long userId = 1L;
        int usingPoint = 10000;

        when(pointService.calculatePointByUserId(userId)).thenReturn(0);
        when(pointService.checkAvailablePoint(pointService.calculatePointByUserId(userId),usingPoint)).thenReturn(usingPoint);


    }

    void calculateAmountAndPrice_HavePoint_NotUsePoint() {

        long userId = 1L;
        int usingPoint = 0;

        when(pointService.calculatePointByUserId(userId)).thenReturn(50000);
        when(pointService.checkAvailablePoint(pointService.calculatePointByUserId(userId),usingPoint)).thenReturn(usingPoint);

        int totalPrice = orderSheet.calculateTotalPrice(
                itemSumPrice,
                deliverFee,
                pointService.calculatePointByUserId(userId));

        assertThat(totalPrice).isEqualTo(32500);

    }

    void calculateAmountAndPrice_HavePoint_UsePoint() {

        long userId = 1L;
        int usingPoint = 10000;

        when(pointService.calculatePointByUserId(userId)).thenReturn(50000);
        when(pointService.checkAvailablePoint(pointService.calculatePointByUserId(userId),usingPoint)).thenReturn(usingPoint);

    }

    void calculateAmountAndPrice_HavePoint_UseManyPoint() {

        long userId = 1L;
        int usingPoint = 60000;

        when(pointService.calculatePointByUserId(userId)).thenReturn(50000);
        when(pointService.checkAvailablePoint(pointService.calculatePointByUserId(userId),usingPoint)).thenReturn(usingPoint);


    }

    @Order(1)
    @Test
    void calculateAmountAndPrice_WithNoPoint(){

        //given
        long userId = 1L;
        int usingPoint = 0;

        when(pointService.calculatePointByUserId(userId)).thenReturn(0);
        when(pointService.checkAvailablePoint(pointService.calculatePointByUserId(userId),usingPoint)).thenReturn(usingPoint);

        int itemSumPrice = 30000;
        int deliverFee = 2500;

        int totalPrice = orderSheet.calculateTotalPrice(
                itemSumPrice,
                deliverFee,
                pointService.calculatePointByUserId(userId));

        assertThat(totalPrice).isEqualTo(32500);
    }

    @Order(2)
    @Test
    void calculateAmountAndPrice_WithPoint(){

        //given
        long userId = 1L;
        int usingPoint = 1000;

        when(pointService.calculatePointByUserId(userId)).thenReturn(3500);

        int itemSumPrice = 30000;
        int deliverFee = 2500;

        int totalPrice = orderSheet.calculateTotalPrice(
                itemSumPrice,
                deliverFee,
                pointService.calculatePointByUserId(userId));

        assertThat(totalPrice).isEqualTo(29000);


    }

    @Order(3)
    @Test
    void calculateAmountAndPrice_WithManyPoint(){

        //given
        long userId = 1L;
        int usingPoint = 1000;

        when(pointService.calculatePointByUserId(userId)).thenReturn(50000);
        when(pointService.checkAvailablePoint(pointService.calculatePointByUserId(userId),usingPoint)).thenReturn(usingPoint);

        int itemSumPrice = 30000;
        int deliverFee = 2500;

        assertThrows(RuntimeException.class,
                () -> orderSheet.calculateTotalPrice(
                        itemSumPrice,
                        deliverFee,
                        pointService.calculatePointByUserId(userId)));

    }

}
