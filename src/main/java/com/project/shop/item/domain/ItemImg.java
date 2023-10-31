package com.project.shop.item.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "itemImg")
@Entity
public class ItemImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemImgId")
    private int itemImgId;     //상품 이미지 번호
    @Column(name = "itemId", nullable = false)
    private int itemId;     //상품 번호
    @Column(name = "imgUrl", nullable = false)
    private String imgUrl;     //이미지 경로
    @Column(name = "imgName", nullable = false)
    private String imgName;    //이미지명
    @Column(name = "mainImg", nullable = false)
    @Enumerated(EnumType.STRING)
    private  ItemImgType itemImgType;    //대표이미지 여부

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //상품이미지 등록일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //상품이미지수정일
}
