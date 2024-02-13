package com.project.shop.admin.item.controller;

import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.item.dto.request.CategoryRequest;
import com.project.shop.item.dto.request.CategoryUpdateRequest;
import com.project.shop.item.dto.response.CategoryResponse;
import com.project.shop.item.service.CategoryService;
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
@Tag( name = "AdminCategoryController", description = "[관리자] 카테고리 API")
public class AdminCategoryController {

    private final CategoryService categoryService;

    //조회
    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponse> categoryFindAll(@AuthenticationPrincipal UserDto userDto){
        return categoryService.categoryFindAll(userDto.getLoginId());
    }

    //등록
    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public void categoryCreate(@AuthenticationPrincipal UserDto userDto, @RequestBody CategoryRequest categoryRequest){
        categoryService.create(userDto.getLoginId(), categoryRequest);
    }

    //수정
    @PutMapping("/categories/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public void categoryUpdate(@AuthenticationPrincipal UserDto userDto, @PathVariable("categoryId") long categoryId, @RequestBody CategoryUpdateRequest categoryUpdateRequest){
        categoryService.update(userDto.getLoginId(), categoryId,categoryUpdateRequest);
    }

    //삭제
    @DeleteMapping("/categories/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void categoryDelete(@AuthenticationPrincipal UserDto userDto, @PathVariable("categoryId") long categoryId){
        categoryService.delete(userDto.getLoginId(), categoryId);
    }

}
