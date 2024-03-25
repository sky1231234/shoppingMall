package com.project.shop.admin.item.controller;


import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.item.dto.request.ItemRequest;
import com.project.shop.item.dto.request.ItemUpdateRequest;
import com.project.shop.item.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping ("/admin/items")
@RequiredArgsConstructor
@Validated
@Tag( name = "AdminItemController", description = "[관리자] 상품 API")
public class AdminItemController {

    private final ItemService itemService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<HttpStatus> create(@AuthenticationPrincipal UserDto userDto, @RequestBody ItemRequest itemRequest){
        long itemId = itemService.create(userDto.getLoginId(), itemRequest);
        return ResponseEntity.created(URI.create("/admin/items"+itemId)).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{itemId}")
    public ResponseEntity<HttpStatus> update(@AuthenticationPrincipal UserDto userDto, @PathVariable("itemId") long itemId, @RequestBody ItemUpdateRequest itemUpdateRequest){
        itemService.update(userDto.getLoginId(), itemId, itemUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{itemId}")
    public ResponseEntity<HttpStatus> delete(@AuthenticationPrincipal UserDto userDto, @PathVariable("itemId") long itemId){
        itemService.delete(userDto.getLoginId(), itemId);
        return ResponseEntity.noContent().build();
    }

}
