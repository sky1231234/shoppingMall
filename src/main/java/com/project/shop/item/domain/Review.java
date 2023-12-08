package com.project.shop.item.domain;

import com.project.shop.item.dto.request.ReviewUpdateRequest;
import com.project.shop.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "review")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long reviewId;     //리뷰번호

    //주인
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId")
    private Item item;     //상품

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User users;  //고객

    @Column(name = "title", nullable = false)
    private String title;    //제목
    @Column(name = "content", nullable = false)
    private String content;    //내용
    @Column(name = "star", nullable = false)
    private int star;    //별점

    @Builder.Default
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewImg> reviewImgList = new ArrayList<>(); //리뷰 이미지

    @Column(name = "insertDate", nullable = false)
    private LocalDateTime insertDate;   //상품 등록일
    @Column(name = "updateDate", nullable = false)
    private LocalDateTime updateDate;   //상품 수정일

    public Review editReview(ReviewUpdateRequest reviewUpdateRequest){
        this.title = reviewUpdateRequest.title();
        this.content = reviewUpdateRequest.content();
        this.star = reviewUpdateRequest.star();
        return this;
    }


}
