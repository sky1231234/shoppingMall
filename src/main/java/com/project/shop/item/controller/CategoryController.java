package com.project.shop.item.controller;

import com.project.shop.item.dto.request.CategoryRequest;
import com.project.shop.item.dto.request.CategoryUpdateRequest;
import com.project.shop.item.dto.response.CategoryResponse;
import com.project.shop.item.dto.response.UserReviewResponse;
import com.project.shop.item.service.CategoryService;
import com.project.shop.item.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api")
@RequiredArgsConstructor
@Validated
public class CategoryController {
    private final CategoryService categoryService;

    //조회
    @GetMapping("/category")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponse> categoryFindAll(){
        return categoryService.categoryFindAll();
    }

    //등록
    @PostMapping("/category")
    @ResponseStatus(HttpStatus.CREATED)
    public void categoryCreate(@RequestBody CategoryRequest categoryRequest){
        categoryService.create(categoryRequest);
    }

    //수정
    @PutMapping("/category/{categoryId}}")
    @ResponseStatus(HttpStatus.OK)
    public void categoryUpdate(@RequestBody CategoryUpdateRequest categoryUpdateRequest){
        categoryService.update(categoryUpdateRequest);
    }

    //삭제
    @DeleteMapping("/category/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public void categoryDelete(@RequestBody long categoryId){
        categoryService.delete(categoryId);
    }

}
