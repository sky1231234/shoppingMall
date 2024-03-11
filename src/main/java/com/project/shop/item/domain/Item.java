package com.project.shop.item.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "item")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long itemId;     //상품번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private Category category;

    @Column(name = "itemName", nullable = false)
    private String itemName;    //상품명
    @Column(name = "price", nullable = false)
    private int price;    //가격
    @Column(name = "explain", nullable = false)
    private String explain;     //상품 설명

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //상품 등록일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //상품 수정일

    @Builder.Default
    @OneToMany(mappedBy = "item")
    private List<ItemImg> itemImgList = new ArrayList<>();

    @Builder
    public Item(Category category, String itemName, int price, String explain, LocalDateTime dateTime) {
        this.category = category;
        this.itemName = itemName;
        this.price = price;
        this.explain = explain;
        this.insertDate = dateTime;
        this.updateDate = dateTime;
    }

    public void updateItemInfo(Category category, String itemName, int price, String explain){
        this.category = category;
        this.itemName = itemName;
        this.price = price;
        this.explain = explain;
    }

}

