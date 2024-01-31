package com.project.shop.item.controller;


import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.item.dto.request.ItemUpdateRequest;
import com.project.shop.item.dto.request.ItemRequest;
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
@RequestMapping ("/api")
@RequiredArgsConstructor
@Validated
@Tag( name = "ItemController", description = "[사용자] 상품 API")
public class ItemController {

    private final ItemService itemService;

    //상품 전체 조회
    @GetMapping("/items")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemListResponse> itemFindAll(@AuthenticationPrincipal UserDto userDto){
        return itemService.itemFindAll(userDto.getLoginId());
    }

    //상품 상세 조회
    @GetMapping("/items/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemResponse itemDetailFind(@AuthenticationPrincipal UserDto userDto, @PathVariable("itemId") long itemId){
        return itemService.itemDetailFind(userDto.getLoginId(), itemId);
    }

}
