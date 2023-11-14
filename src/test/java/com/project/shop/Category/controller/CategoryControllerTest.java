package com.project.shop.Category.controller;

import com.project.shop.item.dto.request.CategoryRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryControllerTest {

    //등록
    @Test
    @DisplayName("카테고리생성_성공")
    String add() throws Exception {
        return "create";
//        categoryService.create(categoryRequest);
    }
}
