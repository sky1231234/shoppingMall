package com.project.shop.item.service;

import com.project.shop.item.domain.Category;
import com.project.shop.item.dto.request.CategoryRequest;
import com.project.shop.item.dto.request.CategoryUpdateRequest;
import com.project.shop.item.dto.response.CategoryResponse;
import com.project.shop.item.repository.CategoryRepository;
import com.project.shop.member.domain.Authority;
import com.project.shop.member.domain.Member;
import com.project.shop.member.repository.AuthorityRepository;
import com.project.shop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository ;
    private final MemberRepository memberRepository ;
    private final AuthorityRepository authorityRepository ;

    //카테고리별로 브랜드 나열
    public List<CategoryResponse> findAll(String loginId){

        authCheck(loginId);

        Map<String,List<Category>> result = categoryRepository.findAll()
                 .stream()
                 .collect(Collectors.groupingBy(Category::getCategoryName));

//        Map<String,List<Category>> result = groupResult.entrySet().stream()
//                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
//                .limit(10)
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new))
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
    public long create(String loginId, CategoryRequest categoryRequest){

        authCheck(loginId);

        //동일한 브랜드명, 카테고리명 조회
        if(categoryRepository.findByCategoryNameAndBrandName(categoryRequest.categoryName(),categoryRequest.brandName()).isPresent()){
            throw new RuntimeException("등록된 카테고리 있음");
        }

        var result = categoryRepository.save(categoryRequest.toEntity());
        return result.getCategoryId();
    }

    //카테고리 수정
    public long update(String loginId, Long categoryId, CategoryUpdateRequest categoryUpdateRequest){

        authCheck(loginId);

        //동일한 브랜드명, 카테고리명 조회
        if(categoryRepository.findByCategoryNameAndBrandName(categoryUpdateRequest.categoryName(),categoryUpdateRequest.brandName()).isPresent()){
            throw new RuntimeException("ALREADY_CATEGORY");
        }

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_CATEGORY_ID"));

        var result = categoryRepository.save(category.updateCategory(categoryUpdateRequest));
        return result.getCategoryId();
    }

    //카테고리 삭제
    public void delete(String loginId, long categoryId){

        authCheck(loginId);

        if(categoryRepository.findById(categoryId).isEmpty()){
            throw new RuntimeException("NOT_FOUND_CATEGORY_ID");
        }

        categoryRepository.deleteById(categoryId);

    }

    //admin 권한 확인
    private void authCheck(String loginId){

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_MEMBER"));
        Authority authority = authorityRepository.findByMember(member)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_AUTH"));

        if(authority.getAuthName().equals("user"))
            throw new RuntimeException("ONLY_ADMIN");
    }

}
