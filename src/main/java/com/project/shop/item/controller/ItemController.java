package com.project.shop.item.controller;


import com.project.shop.item.dto.response.ItemListResponse;
import com.project.shop.item.dto.response.ItemResponse;
import com.project.shop.item.service.ItemServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping ("/items")
@Tag( name = "ItemController", description = "[사용자] 상품 API")
@RequiredArgsConstructor
@RestController
public class ItemController {

    private final ItemServiceImpl itemServiceImpl;

    //상품 전체 조회
    @GetMapping
    public ResponseEntity<List<ItemListResponse>> findAll(){
        return ResponseEntity.ok()
                .body(itemServiceImpl.findAll());
    }

    //상품 상세 조회
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponse> detailFind(@PathVariable("itemId") long itemId){
        return ResponseEntity.ok()
                .body(itemServiceImpl.findItemDetailInfo(itemId));
    }

}
