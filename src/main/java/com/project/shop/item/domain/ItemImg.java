package com.project.shop.item.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "itemImg")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long itemImgId;     //상품 이미지 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId")
    private Item item;     //상품

    @Column(name = "imgUrl", nullable = false)
    private String imgUrl;     //이미지 경로

    @Column(name = "mainImg", nullable = false)
    @Enumerated(EnumType.STRING)
    private  ItemImgType itemImgType;    //대표 이미지 여부

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //상품 이미지 등록일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //상품 이미지 수정일

}
