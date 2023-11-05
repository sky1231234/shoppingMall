package com.project.shop.item.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "review")
@Entity
@Getter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewId")
    private int reviewId;     //리뷰번호

    @Column(name = "itemId", nullable = false)
    private int itemId;     //상품 번호
    @ManyToOne
    @JoinColumn(name = "itemId")
    private Item item;

    @Column(name = "userId", nullable = false)
    private int userId;     //고객 번호
    @Column(name = "title", nullable = false)
    private String title;    //제목
    @Column(name = "content", nullable = false)
    private String content;    //내용
    @Column(name = "star", nullable = false)
    private int star;    //별점

    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY)
    private List<ReviewImg> reviewImgList = new ArrayList<>(); //리뷰 이미지

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //상품 등록일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //상품 수정일

}
