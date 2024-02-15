package com.project.shop.admin.order.controller;

import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.order.dto.response.OrderResponse;
import com.project.shop.order.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/admin/orders")
@RequiredArgsConstructor
@Validated
@Tag( name = "AdminOrderController", description = "[관리자] 주문 API")
public class AdminOrderController {

    private final OrderService orderService;

    //주문내역 전체 조회
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> findAll(@AuthenticationPrincipal UserDto userDto){
        return orderService.findAll(userDto.getLoginId());
    }


}
