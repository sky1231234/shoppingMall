package com.project.shop.item.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
@Table(name = "reviewImg")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long reviewImgId;     //리뷰 이미지 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewId")
    private Review review;     //리뷰 번호

    @Column(name = "imgUrl", nullable = false)
    private String imgUrl;     //이미지 경로

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //리뷰이미지 등록일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //리뷰이미지수정일

}
