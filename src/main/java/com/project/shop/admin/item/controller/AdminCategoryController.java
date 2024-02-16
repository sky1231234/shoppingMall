package com.project.shop.admin.item.controller;

import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.item.dto.request.CategoryRequest;
import com.project.shop.item.dto.request.CategoryUpdateRequest;
import com.project.shop.item.dto.response.CategoryResponse;
import com.project.shop.item.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping ("/admin/categories")
@RequiredArgsConstructor
@Validated
@Tag( name = "AdminCategoryController", description = "[관리자] 카테고리 API")
public class AdminCategoryController {

    private final CategoryService categoryService;

    //조회
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll(@AuthenticationPrincipal UserDto userDto){
        return ResponseEntity.ok()
                .body(categoryService.findAll(userDto.getLoginId()));
    }

    //등록
    @PostMapping
    public ResponseEntity<HttpStatus> create(@AuthenticationPrincipal UserDto userDto, @RequestBody CategoryRequest categoryRequest){
        categoryService.create(userDto.getLoginId(), categoryRequest);
        return ResponseEntity.created(URI.create("/admin/categories")).build();
    }

    //수정
    @PutMapping("/{categoryId}")
    public ResponseEntity<HttpStatus> update(@AuthenticationPrincipal UserDto userDto, @PathVariable("categoryId") long categoryId, @RequestBody CategoryUpdateRequest categoryUpdateRequest){
        categoryService.update(userDto.getLoginId(), categoryId,categoryUpdateRequest);
        return ResponseEntity.ok().build();
    }

    //삭제
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<HttpStatus> delete(@AuthenticationPrincipal UserDto userDto, @PathVariable("categoryId") long categoryId){
        categoryService.delete(userDto.getLoginId(), categoryId);
        return ResponseEntity.noContent().build();
    }

}
