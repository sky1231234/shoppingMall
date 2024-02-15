package com.project.shop.order.controller;

import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.order.dto.request.OrderCancelRequest;
import com.project.shop.order.dto.request.OrderRequest;
import com.project.shop.order.dto.request.OrderUpdateRequest;
import com.project.shop.order.dto.response.OrderDetailResponse;
import com.project.shop.order.dto.response.OrderResponse;
import com.project.shop.order.dto.response.OrderUserResponse;
import com.project.shop.order.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/members/orders")
@RequiredArgsConstructor
@Validated
@Tag( name = "OrderController", description = "[사용자] 주문 API")
public class OrderController {

    private final OrderService orderService;

    //주문내역 조회
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public OrderUserResponse findAll(@AuthenticationPrincipal UserDto userDto){
        return orderService.findAllByUser(userDto.getLoginId());
    }

    //주문내역 상세 조회
    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDetailResponse detailFind(@AuthenticationPrincipal UserDto userDto, @PathVariable("orderId") long orderId){
        return orderService.detailFind(userDto.getLoginId(), orderId);
    }

    //주문 등록
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@AuthenticationPrincipal UserDto userDto, @RequestBody OrderRequest orderRequest){
        orderService.create(userDto.getLoginId(), orderRequest);
    }

    //주문 수정
    @PutMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@AuthenticationPrincipal UserDto userDto, @PathVariable("orderId") long orderId, @RequestBody OrderUpdateRequest orderUpdateRequest){
        orderService.update(userDto.getLoginId(), orderId, orderUpdateRequest);
    }

    //부분취소, 취소 등록
    @PostMapping("/{orderId}/cancels")
    @ResponseStatus(HttpStatus.CREATED)
    public void cancelCreate(@AuthenticationPrincipal UserDto userDto, @PathVariable("orderId") long orderId, @RequestBody OrderCancelRequest orderCancelRequest){
        orderService.cancelCreate(userDto.getLoginId(), orderId, orderCancelRequest);
    }
}
