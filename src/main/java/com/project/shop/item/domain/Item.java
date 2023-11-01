package com.project.shop.item.domain;

import lombok.Getter;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Table(name = "item")
@Entity
@Getter
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemId")
    private int itemId;     //상품번호
    @Column(name = "categoryId", nullable = false)
    private int categoryId;     //카테고리 번호

    @ManyTo
    private Category category;
    @Column(name = "itemName", nullable = false)
    private String itemName;    //상품명
    @Column(name = "price", nullable = false)
    private int price;    //가격
    @Column(name = "explain")
    private String explain;     //상품 설명

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //상품 등록일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //상품 수정일



}
