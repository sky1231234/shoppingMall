package com.project.shop.ordersheet.service;

import com.project.shop.member.domain.Point;
import com.project.shop.member.service.PointService;
import com.project.shop.ordersheet.domain.OrderSheet;
import com.project.shop.ordersheet.dto.request.OrderSheetRequest;
import org.springframework.stereotype.Service;

@Service
public class OrderSheetService {

    private PointService pointService;
    private OrderSheet orderSheet;
    private Point point;

    public void calculateFinalItemPrice(long userId, OrderSheetRequest orderSheetRequest, int usingPoint){

        int point = pointService.calculatePointByUserId(userId);
        int finalPoint = pointService.checkAvailablePoint(point, usingPoint);

//        int itemSumPrice = orderSheet.calculateItemSumPrice(orderSheetRequest.orderItemRequestList());
//        int deliverFee = orderSheet.calculateDeliverFee(itemSumPrice);
//
//        int totalPrice = orderSheet.calculateTotalPrice(
//                itemSumPrice,
//                deliverFee,
//                finalPoint);
    }

}
