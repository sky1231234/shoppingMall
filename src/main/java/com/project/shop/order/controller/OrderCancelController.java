package com.project.shop.order.controller;

import com.project.shop.order.dto.request.OrderCancelRequest;
import com.project.shop.order.dto.request.OrderPartCancelRequest;
import com.project.shop.order.service.OrderCancelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api")
@RequiredArgsConstructor
@Validated
public class OrderCancelController {

    private final OrderCancelService orderCancelService;

    //부분취소 등록
    @PostMapping("/order/partCancel/{orderId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void orderPartCancelCreate(@PathVariable("orderId") int orderId,@RequestBody OrderPartCancelRequest orderPartCancelRequest){
        orderCancelService.partCancelCreate(orderId, orderPartCancelRequest);
    }

    //취소 등록
    @PostMapping("/order/cancel/{orderId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void orderCancelCreate(@PathVariable("orderId") int orderId, @RequestBody OrderCancelRequest orderCancelRequest){
        orderCancelService.orderCancelCreate(orderId, orderCancelRequest);
    }
}
