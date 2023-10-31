package com.project.shop.item.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
@Table(name = "reviewImg")
@Entity
public class ReviewImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewImgId")
    private int reviewImgId;     //리뷰 이미지 번호
    @Column(name = "reviewId", nullable = false)
    private int reviewId;     //리뷰 번호
    @Column(name = "imgUrl", nullable = false)
    private String imgUrl;     //이미지 경로
    @Column(name = "imgName", nullable = false)
    private String imgName;    //이미지명

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //리뷰이미지 등록일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //리뷰이미지수정일
    
}
