package com.project.shop.user.domain;

import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Option;
import com.project.shop.user.dto.request.CartUpdateRequest;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "cart")
@Entity
@Getter
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartId")
    private long cartId;     //장바구니 번호

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Item item;

    @Column(name = "itemId", nullable = false)
    private long itemId;    //상품번호

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private User user;

    //추가???
    @Column(name = "optionNum", nullable = false)
    private long optionNum;    //옵션번호

    @Column(name = "count", nullable = false)
    private int count;    //수량

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //상품 등록일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //상품 수정일

    public void updateCart(CartUpdateRequest cartUpdateRequest){
        this.count = cartUpdateRequest.getCount();
        this.optionNum = cartUpdateRequest.getOptionNum();
    }
}