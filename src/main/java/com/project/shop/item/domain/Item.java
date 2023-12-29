package com.project.shop.item.domain;

import com.project.shop.item.dto.request.ItemUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "item")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    public Item updateCategory(Category category){
        this.category = category;
        return this;
    }

    public Item editItem(Category category, ItemUpdateRequest itemUpdateRequest){
        this.category = category;
        this.itemName = itemUpdateRequest.itemName();
        this.price = itemUpdateRequest.price();
        this.explain = itemUpdateRequest.explain();
        return this;
    }

}

