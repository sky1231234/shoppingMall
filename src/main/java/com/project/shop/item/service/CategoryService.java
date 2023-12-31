package com.project.shop.item.service;

import com.project.shop.item.domain.Category;
import com.project.shop.item.dto.request.CategoryRequest;
import com.project.shop.item.dto.request.CategoryUpdateRequest;
import com.project.shop.item.dto.response.CategoryResponse;
import com.project.shop.item.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository ;

    //브랜드별 조회 - 메인
    //카테고리별로 브랜드 나열
    public List<CategoryResponse> categoryFindAll(){

        Map<String,List<Category>> result = categoryRepository.findAll()
                 .stream()
                 .collect(Collectors.groupingBy(Category::getCategoryName));


        return result.entrySet()
                .stream()
                .map(x -> {
                     var brand = x.getValue().stream()
                            .map(y ->
                            {
                                return CategoryResponse.Brand.builder()
                                        .categoryId(y.getCategoryId())
                                        .brandName(y.getBrandName())
                                        .build();
                            })
                            .toList();

                     return CategoryResponse.builder()
                             .categoryName(x.getKey())
                             .brand(brand)
                             .build();
                })
                .toList();
    }

    //카테고리 등록
    public long create(CategoryRequest categoryRequest){

        //동일한 브랜드명, 카테고리명 조회
        if(categoryRepository.findByCategoryNameAndBrandName(categoryRequest.categoryName(),categoryRequest.brandName()).isPresent()){
            throw new RuntimeException("등록된 카테고리 있음");
        }

        var result = categoryRepository.save(categoryRequest.toEntity());
        return result.getCategoryId();
    }

    //카테고리 수정
    public long update(Long categoryId, CategoryUpdateRequest categoryUpdateRequest){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_CATEGORY_ID"));

        var result = categoryRepository.save(category.updateCategory(categoryUpdateRequest));
        return result.getCategoryId();
    }

    //카테고리 삭제
    public void delete(long categoryId){

        if(categoryRepository.findById(categoryId).isEmpty()){
            throw new RuntimeException("NOT_FOUND_CATEGORY_ID");
        }

        categoryRepository.deleteById(categoryId);

    }

}
