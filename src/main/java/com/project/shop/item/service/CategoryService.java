package com.project.shop.item.service;

import com.project.shop.item.dto.request.CategoryRequest;
import com.project.shop.item.dto.request.CategoryUpdateRequest;
import com.project.shop.item.dto.response.CategoryResponse;
import com.project.shop.item.dto.response.ItemResponse;
import com.project.shop.item.dto.response.ReviewResponse;
import com.project.shop.item.repository.CategoryRepository;
import com.project.shop.item.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository ;

    //카테고리 조회
    //카테고리 이름 별로 / 브랜드 별로 조회?
    public List<CategoryResponse> categoryFindAll(){

        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponse::fromEntity)
                .collect(Collectors.toList());

    }

    //카테고리 등록
    public void create(CategoryRequest categoryRequest){

        //동일한 브랜드명, 카테고리명 조회
        if(categoryRepository.findByCategoryNameAndBrandName(categoryRequest.getCategoryName(),categoryRequest.getBrandName()).isPresent()){
            throw new RuntimeException("등록된 카테고리 있음");
        }

        categoryRepository.save(categoryRequest.toEntity());

    }

    //카테고리 수정
    public void update(Long categoryId, CategoryUpdateRequest categoryUpdateRequest){

        //수정할 카테고리Id 확인
        if(!categoryRepository.findById(categoryId).isPresent()){
            throw new RuntimeException("카테고리Id 없음");
        }
        //수정하기
        // category.updateCategory(categoryUpdateRequest)
//        categoryRepository.save(updateCategory);
    }

    //카테고리 삭제
    public void delete(long categoryId){

        if(!categoryRepository.findById(categoryId).isPresent()){
            throw new RuntimeException("카테고리Id 없음");
        }

        categoryRepository.deleteById(categoryId);
    }
}
