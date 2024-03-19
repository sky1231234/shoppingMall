package com.project.shop.ordersheet.domain;

import com.project.shop.item.domain.Item;
import com.project.shop.member.domain.Address;
import com.project.shop.member.domain.Member;
import com.project.shop.member.exception.MemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
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

    @DisplayName("주문서 생성 테스트")
    @Order(2)
    @Test
    public void createOrderSheet(){

        //given
        List<OrderSheetItem> mockOrderItems = orderSheetItems;
        int usedPoint = 500;
        int itemSumPrice = 148000;
        int deliverFee = 0;
        int finalPrice =  147500;
        Address mockAddress = address;
        LocalDateTime dateTime = LocalDateTime.now();

        //when
        OrderSheet orderSheet = new OrderSheet(mockOrderItems, usedPoint, itemSumPrice, finalPrice, deliverFee, mockAddress, dateTime);

        //then
        assertEquals(mockOrderItems, orderSheet.getOrderSheetItems());
        assertEquals(usedPoint, orderSheet.getUsedPoint());
        assertEquals(itemSumPrice, orderSheet.getItemSumPrice());
        assertEquals(deliverFee, orderSheet.getDeliverFee());
        assertEquals(finalPrice, orderSheet.getFinalPrice());
        assertEquals(mockAddress, orderSheet.getAddress());
        assertEquals(dateTime, orderSheet.getInsertDate());

    }

    @DisplayName("주문서 상품 금액 계산 테스트")
    @Order(2)
    @Test
    public void sumAllPriceAndAmount(){

        //given
        OrderSheet orderSheet = new OrderSheet();

        List<OrderSheetItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderSheetItem(orderSheet,item, 1, 2,24000)); // 상품 가격 10000원, 수량 2개
        orderItems.add(new OrderSheetItem(orderSheet,item, 2, 5,20000)); // 상품 가격 20000원, 수량 1개

        orderSheet.addOrderSheetItem(orderItems);

        //when
        int itemSumPrice = orderSheet.sumAllPriceAndAmount(orderItems);

        //then
        assertThat(itemSumPrice).isEqualTo(148000);

    }

    @DisplayName("상품 가격 > 사용 포인트인 경우 테스트")
    @Order(3)
    @Test
    public void checkAvailablePoint_itemSumPriceMoreThanPoints(){

        //given
        OrderSheet orderSheet = new OrderSheet();

        int point = 5000;
        int itemSumPrice = 148000;
        int deliverFee = 2500;

        //when
        int finalPrice = orderSheet.checkAvailablePoint(itemSumPrice, point,deliverFee);

        //then
        assertThat(finalPrice).isEqualTo(145500);


    }

    @DisplayName("상품 가격 < 사용 포인트인 경우 테스트")
    @Order(4)
    @Test
    public void checkAvailablePoint_PointsMoreThanitemSumPrice(){

        //given
        OrderSheet orderSheet = new OrderSheet();

        int point = 150000;
        int itemSumPrice = 148000;
        int deliverFee = 2500;

        //when
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> orderSheet.checkAvailablePoint(
                        itemSumPrice,
                        point,
                        deliverFee));

        //then
        assertThat(exception.getMessage()).isEqualTo("포인트는 상품 금액 이상 사용할 수 없습니다.");

    }

    @DisplayName("총 금액 계산 테스트")
    @Order(5)
    @Test
    public void calculateFinalPrice(){

        //given
        OrderSheet orderSheet = new OrderSheet();

        int point = 5000;
        int itemSumPrice = 148000;
        int deliverFee = 2500;

        //when
        int finalPrice = orderSheet.calculateTotalPriceAfterPoints(itemSumPrice, point, deliverFee);

        //then
        assertThat(finalPrice).isEqualTo(145500);
    }


}
