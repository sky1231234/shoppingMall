package com.project.shop.item.domain;

import com.project.shop.item.dto.request.CategoryUpdateRequest;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "category")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

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

    @Builder
    public Category(String categoryName, String brandName,LocalDateTime dateTime){
        this.categoryName = categoryName;
        this.brandName = brandName;
        this.insertDate = dateTime;
        this.updateDate = dateTime;
    }
    public Category updateCategory(CategoryUpdateRequest categoryUpdateRequest){
        this.categoryName = categoryUpdateRequest.categoryName();
        this.brandName = categoryUpdateRequest.brandName();
        this.updateDate = LocalDateTime.now();
        return this;
    }

}
