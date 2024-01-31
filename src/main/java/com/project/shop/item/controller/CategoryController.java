package com.project.shop.item.controller;

import com.project.shop.item.dto.response.CategoryResponse;
import com.project.shop.item.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api")
@RequiredArgsConstructor
@Validated
@Tag( name = "CategoryController", description = "[사용자] 카테고리 API")
public class CategoryController {

    private final CategoryService categoryService;


}
