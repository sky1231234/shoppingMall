package com.project.shop.item.service;

import com.project.shop.item.domain.Category;
import com.project.shop.item.dto.request.CategoryRequest;
import com.project.shop.item.dto.request.CategoryUpdateRequest;
import com.project.shop.item.dto.response.CategoryResponse;
import com.project.shop.item.dto.response.ItemResponse;
import com.project.shop.item.dto.response.ReviewResponse;
import com.project.shop.item.repository.CategoryRepository;
import com.project.shop.item.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository ;

    //브랜드별 조회 - 메인
    //브랜드별로 카테고리 나열
    public List<CategoryResponse> categoryFindAll(){

        return categoryRepository.findByCategoryName()
                .stream()
                .map(CategoryResponse::fromEntity)
                .collect(Collectors.toList());

    }

    //카테고리 등록
    public void create(CategoryRequest categoryRequest){

        //동일한 브랜드명, 카테고리명 조회
        if(categoryRepository.findByCategoryNameAndBrandName(categoryRequest.categoryName(),categoryRequest.brandName()).isPresent()){
            throw new RuntimeException("등록된 카테고리 있음");
        }

        categoryRepository.save(categoryRequest.toEntity());

    }

    //카테고리 수정
    public void update(Long categoryId, CategoryUpdateRequest categoryUpdateRequest){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_CATEGORY_ID"));

        categoryRepository.save(category.updateCategory(categoryUpdateRequest));
    }

    //카테고리 삭제
    public void delete(long categoryId){

        if(categoryRepository.findById(categoryId).isEmpty()){
            throw new RuntimeException("NOT_FOUND_CATEGORY_ID");
        }

        categoryRepository.deleteById(categoryId);
    }

}
