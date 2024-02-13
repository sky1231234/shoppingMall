package com.project.shop.admin.item.controller;


import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.item.dto.request.ItemRequest;
import com.project.shop.item.dto.request.ItemUpdateRequest;
import com.project.shop.item.dto.response.ItemListResponse;
import com.project.shop.item.dto.response.ItemResponse;
import com.project.shop.item.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/admin")
@RequiredArgsConstructor
@Validated
@Tag( name = "AdminItemController", description = "[관리자] 상품 API")
public class AdminItemController {

    private final ItemService itemService;

    //상품 등록
    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public void itemCreate(@AuthenticationPrincipal UserDto userDto, @RequestBody ItemRequest itemRequest){
        itemService.create(userDto.getLoginId(), itemRequest);
    }

    //상품 수정
    @PutMapping("/items/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public void itemUpdate(@AuthenticationPrincipal UserDto userDto, @PathVariable("itemId") long itemId, @RequestBody ItemUpdateRequest itemUpdateRequest){
        itemService.update(userDto.getLoginId(), itemId, itemUpdateRequest);
    }

    //상품 삭제
    @DeleteMapping("/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void itemDelete(@AuthenticationPrincipal UserDto userDto, @PathVariable("itemId") long itemId){
        itemService.delete(userDto.getLoginId(), itemId);
    }

}
