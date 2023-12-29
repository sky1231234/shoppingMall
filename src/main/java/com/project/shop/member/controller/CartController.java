package com.project.shop.member.controller;

import com.project.shop.member.dto.request.CartRequest;
import com.project.shop.member.dto.request.CartUpdateRequest;
import com.project.shop.member.dto.response.CartResponse;
import com.project.shop.member.service.CartService;
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
    @GetMapping("/carts/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CartResponse> cartFindByUserId(@PathVariable("userId") long userId){
        return cartService.cartFindByUserId(userId);
    }

    //장바구니 등록
    @PostMapping("/carts")
    @ResponseStatus(HttpStatus.CREATED)
    public void cartCreate(@RequestBody long userId, CartRequest cartRequest){
        cartService.create(userId, cartRequest);
    }

    //장바구니 수정
    @PutMapping("/carts/{cartId}")
    @ResponseStatus(HttpStatus.OK)
    public void cartUpdate(@PathVariable("cartId") long cartId, @RequestBody CartUpdateRequest cartUpdateRequest){
        cartService.update(cartId, cartUpdateRequest);
    }

    //장바구니 삭제
    @DeleteMapping("/carts/{cartId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cartDelete(@PathVariable("cartId") long cartId){
        cartService.delete(cartId);
    }

}
