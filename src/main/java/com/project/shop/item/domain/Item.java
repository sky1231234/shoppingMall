package com.project.shop.item.domain;

import com.project.shop.item.dto.request.ItemUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemId")
    private long itemId;     //상품번호

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @Column(name = "itemName", nullable = false)
    private String itemName;    //상품명
    @Column(name = "price", nullable = false)
    private int price;    //가격
    @Column(name = "explain", nullable = false)
    private String explain;     //상품 설명

    //양방향 매핑 - mappedBy된 곳은 save가 안 될 수도 => 자식에 save하면 저장 안됨, 주인에 전달하여 save하기
    //자식
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemImg> itemImgList = new ArrayList<>(); //상품 이미지 리스트

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Option> optionList = new ArrayList<>(); //옵션 리스트

//    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
//    private List<Review> reviewList = new ArrayList<>(); //리뷰 리스트


    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //상품 등록일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //상품 수정일

    public void updateCategory(Category category){
        this.category = category;
    }

    public void editItem(Category category, ItemUpdateRequest itemUpdateRequest){
        this.category = category;
        this.itemName = itemUpdateRequest.getItemName();
        this.price = itemUpdateRequest.getPrice();
        this.explain = itemUpdateRequest.getExplain();
    }

}

