package com.project.shop.item.controller;

import com.project.shop.item.dto.response.CategoryResponse;
import com.project.shop.item.service.CategoryService;
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
    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponse> categoryFindAll(){
        return categoryService.categoryFindAll();
    }

}
