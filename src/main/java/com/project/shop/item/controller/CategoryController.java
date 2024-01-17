package com.project.shop.item.controller;

import com.project.shop.item.domain.Category;
import com.project.shop.item.dto.request.CategoryRequest;
import com.project.shop.item.dto.request.CategoryUpdateRequest;
import com.project.shop.item.dto.response.CategoryResponse;
import com.project.shop.item.dto.response.UserReviewResponse;
import com.project.shop.item.service.CategoryService;
import com.project.shop.item.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping ("/api")
@RequiredArgsConstructor
@Validated
public class CategoryController {

    private final CategoryService categoryService;

    //조회
    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponse> categoryFindAll(){
        return categoryService.categoryFindAll();
    }

    //등록
    //admin 권한
    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public void categoryCreate(@RequestBody CategoryRequest categoryRequest){
        categoryService.create(categoryRequest);
    }

    //수정
    //admin 권한
    @PutMapping("/categories/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public void categoryUpdate(@PathVariable("categoryId") long categoryId, @RequestBody CategoryUpdateRequest categoryUpdateRequest){
        categoryService.update(categoryId,categoryUpdateRequest);
    }

    //삭제
    //admin 권한
    @DeleteMapping("/categories/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void categoryDelete(@PathVariable("categoryId") long categoryId){
        categoryService.delete(categoryId);
    }

}
