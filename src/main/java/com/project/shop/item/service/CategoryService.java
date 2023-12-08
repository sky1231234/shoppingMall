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

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository ;

    //브랜드별 조회 - 메인
    //브랜드별로 카테고리 나열
    public List<CategoryResponse> categoryFindAll(){

         var category = categoryRepository.findCategoryName()
                .stream()
                .map( x -> {
                    System.out.println("x");
                    System.out.println(x.getCategoryName());

                    List<CategoryResponse.BrandList> brandList = categoryRepository.findBrand(x.getCategoryName());

                    var brand = brandList.stream()
                            .map(y ->
                                {
                                    return CategoryResponse.BrandList.builder()
                                            .categoryId(y.getCategoryId())
                                            .brandName(y.getBrandName())
                                            .build();
                                })
                            .toList();

                    return CategoryResponse.builder()
                            .categoryName(x.getCategoryName())
                            .brand(brand)
                            .build();
                })
                .toList();


        return category;
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
