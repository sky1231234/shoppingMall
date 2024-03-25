package com.project.shop.order.controller;

import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.order.dto.request.OrderCancelRequest;
import com.project.shop.order.dto.request.OrderUpdateRequest;
import com.project.shop.order.dto.response.OrderDetailResponse;
import com.project.shop.order.dto.response.OrderUserResponse;
import com.project.shop.order.service.OrderService;
import com.project.shop.order.dto.request.OrderRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping ("/orders")
@RequiredArgsConstructor
@Validated
@Tag( name = "OrderController", description = "[사용자] 주문 API")
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<OrderUserResponse> findAllByMember(@AuthenticationPrincipal UserDto userDto){
        return ResponseEntity.ok()
                .body(orderService.findAllByMember(userDto.getLoginId()));
    }

    @PreAuthorize("hasRole('ADMIN','USER')")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailResponse> detailFind(@AuthenticationPrincipal UserDto userDto, @PathVariable("orderId") long orderId){
        return ResponseEntity.ok()
                .body(orderService.detailFind(userDto.getLoginId(), orderId));
    }

    @PreAuthorize("hasRole('ADMIN','USER')")
    @PostMapping
    public ResponseEntity<HttpStatus> create(@AuthenticationPrincipal UserDto userDto, @RequestBody OrderRequest orderRequest){
        long orderId = orderService.create(userDto.getLoginId(), orderRequest);
        return ResponseEntity.created(URI.create("/members/orders"+orderId)).build();
    }

    @PreAuthorize("hasRole('ADMIN','USER')")
    @PutMapping("/{orderId}")
    public ResponseEntity<HttpStatus> update(@AuthenticationPrincipal UserDto userDto, @PathVariable("orderId") long orderId, @RequestBody OrderUpdateRequest orderUpdateRequest){
        orderService.update(userDto.getLoginId(), orderId, orderUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN','USER')")
    @PostMapping("/{orderId}/cancels")
    public ResponseEntity<HttpStatus> cancelCreate(@AuthenticationPrincipal UserDto userDto, @PathVariable("orderId") long orderId, @RequestBody OrderCancelRequest orderCancelRequest){
        long orderCancelId = orderService.cancelCreate(userDto.getLoginId(), orderId, orderCancelRequest);
        return ResponseEntity.created(URI.create("/members/orders/"+orderId+"/cancels"+orderCancelId)).build();
    }
}
