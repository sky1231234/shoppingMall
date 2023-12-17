package com.project.shop.item.domain;

import com.project.shop.global.common.BaseTimeEntity;
import com.project.shop.item.dto.request.CategoryUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "category")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long categoryId;     //카테고리 번호

    @Column(name = "categoryName", nullable = false)
    private String categoryName;    //카테고리 이름

    @Column(name = "brandName", nullable = false)
    private String brandName;    //브랜드 이름

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //상품 등록일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //상품 수정일

    public Category updateCategory(CategoryUpdateRequest categoryUpdateRequest){
        this.categoryName = categoryUpdateRequest.categoryName();
        this.brandName = categoryUpdateRequest.brandName();
        this.updateDate = LocalDateTime.now();
        return this;
    }

}
