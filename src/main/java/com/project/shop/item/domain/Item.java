package com.project.shop.item.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "item")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemId")
    private long itemId;     //상품번호
    @Column(name = "categoryId", nullable = false)
    private long categoryId;     //카테고리 번호

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
    @Column(name = "itemName", nullable = false)
    private String itemName;    //상품명
    @Column(name = "price", nullable = false)
    private int price;    //가격
    @Column(name = "explain")
    private String explain;     //상품 설명

    @OneToMany(mappedBy = "item")
    private List<ItemImg> itemImgList = new ArrayList<>(); //상품 이미지 리스트

    @OneToMany(mappedBy = "item")
    private List<Option> optionList = new ArrayList<>();
    @OneToMany(mappedBy = "item")
    private List<Review> reviewList = new ArrayList<>();
    @OneToMany(mappedBy = "item")
    private List<ReviewImg> reviewImgList = new ArrayList<>();

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //상품 등록일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //상품 수정일



}
