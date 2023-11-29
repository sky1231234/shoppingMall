package com.project.shop.user.controller;

import com.project.shop.user.dto.request.CartRequest;
import com.project.shop.user.dto.request.CartUpdateRequest;
import com.project.shop.user.dto.response.CartResponse;
import com.project.shop.user.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api")
@RequiredArgsConstructor
@Validated
public class CartController {

    private final CartService cartService;

    //회원별 장바구니 조회
    @GetMapping("/cart/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CartResponse> cartFindByUserId(@PathVariable("userId") long userId){
        return cartService.cartFindByUserId(userId);
    }

    //장바구니 등록
    @PostMapping("/cart")
    @ResponseStatus(HttpStatus.CREATED)
    public void cartCreate(@RequestBody long userId, CartRequest cartRequest){
        cartService.create(userId, cartRequest);
    }

    //장바구니 수정
    @PutMapping("/cart/{cartId}}")
    @ResponseStatus(HttpStatus.OK)
    public void cartUpdate(@PathVariable("cartId") long cartId, @RequestBody CartUpdateRequest cartUpdateRequest){
        cartService.update(cartId, cartUpdateRequest);
    }

    //장바구니 삭제
    @DeleteMapping("/cart/{cartId}")
    @ResponseStatus(HttpStatus.OK)
    public void cartDelete(@PathVariable("cartId") long cartId){
        cartService.delete(cartId);
    }

}
