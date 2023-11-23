package com.project.shop.item.dto.response;

import com.project.shop.item.domain.Review;
import com.project.shop.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

//setter test해보기 오류나면 setter 필요한것
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemInReviewResponse {

    private User user; //회원id
    private String title;   //리뷰 제목
    private String content; //리뷰 내용
    private int star;    //별점
    private List<ReviewImgResponse> reviewImgResponses; //리뷰 이미지
    private LocalDateTime insertDate;   //리뷰 등록일

    //review -> ReviewResponse
    public static ItemInReviewResponse fromEntity(Review review){

        var reviewImgList = review.getReviewImgList()
                .stream().map(x -> new ReviewImgResponse(x.getImgUrl()))
                .collect(Collectors.toList());

        return ItemInReviewResponse.builder()
                .user(review.getUser())
                .title(review.getTitle())
                .content(review.getContent())
                .star(review.getStar())
                .reviewImgResponses(reviewImgList)
                .insertDate(review.getInsertDate())
                .build();
    }
}