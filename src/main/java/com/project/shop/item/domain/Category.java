package com.project.shop.item.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
@Table(name = "category")
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId")
    private int categoryId;     //카테고리 번호
    @Column(name = "categoryName", nullable = false)
    private String categoryName;    //카테고리 이름
    @Column(name = "brandName", nullable = false)
    private String brandName;    //브랜드 이름

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //상품 등록일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //상품 수정일

}
