package com.project.shop.item.controller;


import com.project.shop.item.dto.response.ItemListResponse;
import com.project.shop.item.dto.response.ItemResponse;
import com.project.shop.item.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping ("/users/items")
@Tag( name = "ItemController", description = "[사용자] 상품 API")
@RequiredArgsConstructor
@RestController
public class ItemController {

    private final ItemService itemService;

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<ItemListResponse>> findAll(){
        return ResponseEntity.ok()
                .body(itemService.findAll());
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponse> detailFind(@PathVariable("itemId") long itemId){
        //여기 들어오기전에 admin 체크한다 여기서

        return ResponseEntity.ok()
                .body(itemService.findItemDetailInfo(itemId));
    }

}
