package com.project.shop.member.controller;

import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.item.dto.response.ReviewResponse;
import com.project.shop.member.dto.request.CartRequest;
import com.project.shop.member.dto.request.CartUpdateRequest;
import com.project.shop.member.service.CartService;
import com.project.shop.member.dto.response.CartResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping ("/carts")
@RequiredArgsConstructor
@Validated
@Tag( name = "CartController", description = "[사용자] 장바구니 API")
public class CartController {

    private final CartService cartService;

    @PreAuthorize("hasRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<CartResponse>> findAll(@AuthenticationPrincipal UserDto userDto){
        return ResponseEntity.ok()
                .body(cartService.findAll(userDto.getLoginId()));
    }

    @PreAuthorize("hasRole('ADMIN','USER')")
    @PostMapping
    public ResponseEntity<ReviewResponse> create(@AuthenticationPrincipal UserDto userDto, @RequestBody CartRequest cartRequest){
        long cartId = cartService.create(userDto.getLoginId(), cartRequest);
        return ResponseEntity.created(URI.create("/members/carts"+cartId)).build();

    }

    @PreAuthorize("hasRole('ADMIN','USER')")
    @PutMapping("/{cartId}")
    public ResponseEntity<ReviewResponse> update(@AuthenticationPrincipal UserDto userDto, @PathVariable("cartId") long cartId, @RequestBody CartUpdateRequest cartUpdateRequest){
        cartService.update(userDto.getLoginId(), cartId, cartUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN','USER')")
    @DeleteMapping("/{cartId}")
    public ResponseEntity<ReviewResponse> delete(@AuthenticationPrincipal UserDto userDto, @PathVariable("cartId") long cartId){
        cartService.delete(userDto.getLoginId(), cartId);
        return ResponseEntity.noContent().build();

    }

}
