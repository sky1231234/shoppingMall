package com.project.shop.item.controller;


import com.project.shop.item.dto.request.ItemUpdateRequest;
import com.project.shop.item.dto.request.ItemRequest;
import com.project.shop.item.dto.response.ItemResponse;
import com.project.shop.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/item")
@RequiredArgsConstructor
@Validated
public class ItemController {

    private final ItemService itemService;

    //상품 전체 조회
    @GetMapping("/item")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemResponse> itemFindAll(){
        return itemService.itemFindAll();
    }

    //상품 상세 조회
    @GetMapping("/item/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemResponse itemDetailFind(@RequestBody long itemId){
        return itemService.itemDetailFind(itemId);
    }

    //상품 등록
    @PostMapping("/item")
    @ResponseStatus(HttpStatus.CREATED)
    public void itemCreate(@RequestBody ItemRequest itemRequest){
        itemService.create(itemRequest);
    }

    //상품 수정
    @PutMapping("/item/{itemId}}")
    @ResponseStatus(HttpStatus.OK)
    public void itemUpdate(@RequestBody ItemUpdateRequest itemUpdateRequest){
        itemService.update(itemUpdateRequest);
    }

    //상품 삭제
    @DeleteMapping("/item/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public void itemDelete(@RequestBody long itemId ){
        itemService.delete(itemId);
    }

}
