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
public class UserInReviewResponse {

    private User user; //회원id
    private String title;   //리뷰 제목
    private String content; //리뷰 내용
    private int star;    //별점
    private LocalDateTime insertDate;   //리뷰 등록일

    public static UserInReviewResponse fromEntity(Review review){

               return UserInReviewResponse.builder()
                .user(review.getUser())
                .title(review.getTitle())
                .content(review.getContent())
                .star(review.getStar())
                .insertDate(review.getInsertDate())
                .build();
    }
}