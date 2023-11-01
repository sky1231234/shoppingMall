package com.project.shop.item.controller;


import com.project.shop.item.dto.ItemResponse;
import com.project.shop.item.service.ItemService;
import com.project.shop.user.dto.MyInfoEditRequest;
import com.project.shop.user.dto.UserResponse;
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
    @GetMapping("/items")
    @ResponseStatus(HttpStatus.OK)
    public ItemResponse myInfo(  ){
        return itemService.myInfo();
    }
    //상품 상세 조회
    @GetMapping("/items")
    @ResponseStatus(HttpStatus.OK)
    public ItemResponse myInfo(  ){
        return itemService.myInfo();
    }

    //상품 등록

    //상품 수정
    @PutMapping("/{id}/edit")
    @ResponseStatus(HttpStatus.OK)
    public void myInfoEdit(@RequestBody MyInfoEditRequest myInfoEditRequest){
        authService.edit(myInfoEditRequest);
    }

    //상품 삭제


}
