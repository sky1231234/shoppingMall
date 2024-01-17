package com.project.shop.member.controller;

import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.member.dto.request.CartRequest;
import com.project.shop.member.dto.request.CartUpdateRequest;
import com.project.shop.member.dto.response.CartResponse;
import com.project.shop.member.service.CartService;
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
public class CartController {

    private final CartService cartService;

    //장바구니 조회
    @GetMapping("/carts")
    @ResponseStatus(HttpStatus.OK)
    public List<CartResponse> cartFindByUser(@AuthenticationPrincipal UserDto userDto){
        return cartService.cartFindByUser(userDto);
    }

    //장바구니 등록
    @PostMapping("/carts")
    @ResponseStatus(HttpStatus.CREATED)
    public void cartCreate(@AuthenticationPrincipal UserDto userDto, CartRequest cartRequest){
        cartService.create(userDto, cartRequest);
    }

    //장바구니 수정
    @PutMapping("/carts/{cartId}")
    @ResponseStatus(HttpStatus.OK)
    public void cartUpdate(@AuthenticationPrincipal UserDto userDto, @PathVariable("cartId") long cartId, @RequestBody CartUpdateRequest cartUpdateRequest){
        cartService.update(userDto, cartId, cartUpdateRequest);
    }

    //장바구니 삭제
    @DeleteMapping("/carts/{cartId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cartDelete(@AuthenticationPrincipal UserDto userDto, @PathVariable("cartId") long cartId){
        cartService.delete(userDto, cartId);
    }

}
