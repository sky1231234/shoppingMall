package com.project.shop.item.dto.response;

import com.project.shop.item.domain.Review;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemReviewResponse {
    private String categoryName;    //카테고리명
    private String brandName;       //브랜드명
    private String itemName;    //상품명
    private String itemThumbnail;   //상품 대표이미지

    private List<Review> reviewList; //리뷰 리스트
//    private int userId; //회원id
//    private String title;   //리뷰 제목
//    private String content; //리뷰 내용
//    private String star;    //별점
//    private LocalDateTime insertDate;   //리뷰 등록일

    //대표 이미지 불러오기
    public static ItemReviewResponse itemReviewResponse(Review review){
        return ItemReviewResponse.builder()
                .categoryName(review.getItem().getCategory().getCategoryName())
                .brandName(review.getItem().getCategory().getBrandName())
                .itemName(review.getItem().getItemName())
                .itemThumbnail(ReviewImgResponse.getMainUrl(review.getItemId()))
                .reviewList(ReviewResponse.builder()
                        .userId()
                        .title()
                        .content()
                        .star()
                        .insertDate()
                )
                .build();
    }
}