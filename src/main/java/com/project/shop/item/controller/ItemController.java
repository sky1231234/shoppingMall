package com.project.shop.item.controller;


import com.project.shop.item.dto.request.ItemEditRequest;
import com.project.shop.item.dto.request.ItemRequest;
import com.project.shop.item.dto.response.ItemResponse;
import com.project.shop.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/item")
@RequiredArgsConstructor
@Validated
public class ItemController {

    private final ItemService itemService;

    //상품 전체 조회
    @GetMapping("/item")
    @ResponseStatus(HttpStatus.OK)
    public ItemResponse allItem(){
        return itemService.itemAllList();
    }
    //상품 상세 조회
    @GetMapping("/item/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemResponse detailItem(@RequestBody int itemId){
        return itemService.itemDetailList(itemId);
    }

    //상품 등록
    @PostMapping("/item/enroll")
    @ResponseStatus(HttpStatus.CREATED)
    public void itemEnroll(@RequestBody ItemRequest itemRequest){
        itemService.itemEnroll(itemRequest);
    }

    //상품 수정
    @PutMapping("/item/{itemId}}")
    @ResponseStatus(HttpStatus.OK)
    public void itemEdit(@RequestBody ItemEditRequest itemEditRequest){
        itemService.edit(itemEditRequest);
    }

    //상품 삭제
    @DeleteMapping("/item/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public void itemDelete(@RequestBody int itemId ){
        itemService.delete(itemId);
    }

}
