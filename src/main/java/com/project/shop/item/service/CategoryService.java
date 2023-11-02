package com.project.shop.item.service;

import com.project.shop.item.dto.request.CategoryEditRequest;
import com.project.shop.item.dto.request.CategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    //카테고리 조회
    public CategoryResponse categoryDetailList(int categoryId){
        CategoryResponse categoryResponse = null;
        return categoryResponse;
    }

    //카테고리 등록
    public void categoryEnroll(CategoryRequest categoryRequest){

    }

    //카테고리 수정
    public void edit(CategoryEditRequest categoryEditRequest){

    }

    //카테고리 삭제
    public void delete(int categoryId){

    }
}
