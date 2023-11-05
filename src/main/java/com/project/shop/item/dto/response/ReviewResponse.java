package com.project.shop.item.dto.response;

import com.project.shop.item.domain.Review;
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
public class ReviewResponse {
    private String categoryName;    //카테고리명
    private String brandName;       //브랜드명
    private String itemName;    //상품명
    private int userId; //회원id
    private String title;   //리뷰 제목
    private String content; //리뷰 내용
    private int star;    //별점
    private List<ReviewImgResponse> reviewImgResponses; //리뷰 이미지
    private LocalDateTime insertDate;   //리뷰 등록일


    public static ReviewResponse fromEntity(Review review){
        var list = review.getReviewImgList()
                .stream().map(x -> new ReviewImgResponse(x.getImgUrl()))
                .collect(Collectors.toList());

        return ReviewResponse.builder()
                .categoryName(review.getItem().getCategory().getCategoryName())
                .brandName(review.getItem().getCategory().getBrandName())
                .itemName(review.getItem().getItemName())
                .userId(review.getUserId())
                .title(review.getTitle())
                .content(review.getContent())
                .star(review.getStar())
                .reviewImgResponses(list)
                .insertDate(review.getInsertDate())
                .build();
    }
}