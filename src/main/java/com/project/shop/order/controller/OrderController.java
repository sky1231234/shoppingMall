package com.project.shop.order.controller;

import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.order.dto.request.OrderCancelRequest;
import com.project.shop.order.dto.request.OrderRequest;
import com.project.shop.order.dto.request.OrderUpdateRequest;
import com.project.shop.order.dto.response.OrderDetailResponse;
import com.project.shop.order.dto.response.OrderUserResponse;
import com.project.shop.order.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping ("/members/orders")
@RequiredArgsConstructor
@Validated
@Tag( name = "OrderController", description = "[사용자] 주문 API")
public class OrderController {

    private final OrderService orderService;

    //주문내역 조회
    @GetMapping
    public ResponseEntity<OrderUserResponse> findAllByMember(@AuthenticationPrincipal UserDto userDto){
        return ResponseEntity.ok()
                .body(orderService.findAllByMember(userDto.getLoginId()));
    }

    //주문내역 상세 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailResponse> detailFind(@AuthenticationPrincipal UserDto userDto, @PathVariable("orderId") long orderId){
        return ResponseEntity.ok()
                .body(orderService.detailFind(userDto.getLoginId(), orderId));
    }

    //주문 등록
    @PostMapping
    public ResponseEntity<HttpStatus> create(@AuthenticationPrincipal UserDto userDto, @RequestBody OrderRequest orderRequest){
        orderService.create(userDto.getLoginId(), orderRequest);
        return ResponseEntity.created(URI.create("/members/orders")).build();
    }

    //주문 수정
    @PutMapping("/{orderId}")
    public ResponseEntity<HttpStatus> update(@AuthenticationPrincipal UserDto userDto, @PathVariable("orderId") long orderId, @RequestBody OrderUpdateRequest orderUpdateRequest){
        orderService.update(userDto.getLoginId(), orderId, orderUpdateRequest);
        return ResponseEntity.ok().build();
    }

    //부분취소, 취소 등록
    @PostMapping("/{orderId}/cancels")
    public ResponseEntity<HttpStatus> cancelCreate(@AuthenticationPrincipal UserDto userDto, @PathVariable("orderId") long orderId, @RequestBody OrderCancelRequest orderCancelRequest){
        orderService.cancelCreate(userDto.getLoginId(), orderId, orderCancelRequest);
        return ResponseEntity.created(URI.create("/members/orders/"+orderId+"/cancels")).build();
    }
}
