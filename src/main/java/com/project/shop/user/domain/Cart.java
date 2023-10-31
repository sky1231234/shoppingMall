package com.project.shop.user.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "cart")
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartId")
    private int cartId;     //장바구니 번호
    @Column(name = "itemId", nullable = false)
    private int itemId;     //상품 번호
    @Column(name = "userId", nullable = false)
    private int userId;     //고객 번호
    @Column(name = "quantity", nullable = false)
    private int quantity;    //수량

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //상품 등록일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //상품 수정일

}