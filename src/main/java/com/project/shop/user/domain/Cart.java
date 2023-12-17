package com.project.shop.user.domain;

import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Option;
import com.project.shop.user.dto.request.CartUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "cart")
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long cartId;     //장바구니 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User users;

    @Column(name = "optionId", nullable = false)
    private long optionId;    //옵션번호

    @Column(name = "count", nullable = false)
    private int count;    //수량

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //상품 등록일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //상품 수정일

    public Cart updateCart(CartUpdateRequest cartUpdateRequest){
        this.count = cartUpdateRequest.count();
        this.optionId = cartUpdateRequest.optionNum();
        this.updateDate = LocalDateTime.now();
        return this;
    }

    public Cart updateCount(){
        this.count += 1;
        this.updateDate = LocalDateTime.now();
        return this;
    }
}