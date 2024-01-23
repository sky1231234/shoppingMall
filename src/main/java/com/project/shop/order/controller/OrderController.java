package com.project.shop.order.controller;

import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.order.dto.request.OrderCancelRequest;
import com.project.shop.order.dto.request.OrderRequest;
import com.project.shop.order.dto.request.OrderUpdateRequest;
import com.project.shop.order.dto.response.OrderDetailResponse;
import com.project.shop.order.dto.response.OrderResponse;
import com.project.shop.order.dto.response.OrderUserResponse;
import com.project.shop.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    //admin
    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> orderFindAll(@AuthenticationPrincipal UserDto userDto){
        return orderService.orderFindAll(userDto.getLoginId());
    }

    //주문내역 회원별 조회
    @GetMapping("/users/orders")
    @ResponseStatus(HttpStatus.OK)
    public OrderUserResponse orderFindByUser(@AuthenticationPrincipal UserDto userDto){
        return orderService.orderFindByUser(userDto.getLoginId());
    }

    //주문내역 상세 조회
    @GetMapping("/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDetailResponse orderDetailFind(@AuthenticationPrincipal UserDto userDto, @PathVariable("orderId") long orderId){
        return orderService.orderDetailFind(userDto.getLoginId(), orderId);
    }

    //주문 등록
    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public void orderCreate(@AuthenticationPrincipal UserDto userDto, @RequestBody OrderRequest orderRequest){
        orderService.create(userDto.getLoginId(), orderRequest);
    }

    //주문 수정
    @PutMapping("/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public void orderUpdate(@AuthenticationPrincipal UserDto userDto, @PathVariable("orderId") long orderId, @RequestBody OrderUpdateRequest orderUpdateRequest){
        orderService.update(userDto.getLoginId(), orderId, orderUpdateRequest);
    }

    //부분취소, 취소 등록
    @PostMapping("/orders/{orderId}/cancels")
    @ResponseStatus(HttpStatus.CREATED)
    public void orderCancelCreate(@AuthenticationPrincipal UserDto userDto, @PathVariable("orderId") long orderId, @RequestBody OrderCancelRequest orderCancelRequest){
        orderService.orderCancelCreate(userDto.getLoginId(), orderId, orderCancelRequest);
    }
}
