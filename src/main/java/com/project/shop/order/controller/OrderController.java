package com.project.shop.order.controller;

import com.project.shop.order.dto.request.OrderRequest;
import com.project.shop.order.dto.request.OrderUpdateRequest;
import com.project.shop.order.dto.response.OrderDetailResponse;
import com.project.shop.order.dto.response.OrderItemResponse;
import com.project.shop.order.dto.response.OrderResponse;
import com.project.shop.order.dto.response.OrderUserResponse;
import com.project.shop.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    //주문내역 조회
    @GetMapping("/order")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> orderFindAll(){
        return orderService.orderFindAll();
    }

    //주문내역 회원별 조회
    @GetMapping("/order/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderUserResponse orderFindByUser(@PathVariable("userId") int userId){
        return orderService.orderFindByUser(userId);
    }

    //주문내역 상세 조회
    @GetMapping("/order/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDetailResponse orderDetailFind(@PathVariable("orderId") long orderId){
        return orderService.orderDetailFind(orderId);
    }

    //주문 등록
    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public void orderCreate(@RequestBody OrderRequest orderRequest){
        orderService.create(orderRequest);
    }

    //주문 수정
    @PutMapping("/order/{orderId}}")
    @ResponseStatus(HttpStatus.OK)
    public void orderUpdate(@PathVariable("orderId") long orderId, @RequestBody OrderUpdateRequest orderUpdateRequest){
        orderService.update(orderId, orderUpdateRequest);
    }

}
