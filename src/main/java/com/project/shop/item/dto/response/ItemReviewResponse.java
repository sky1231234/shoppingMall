package com.project.shop.item.dto.response;

import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.Review;
import com.project.shop.item.repository.ItemImgRepository;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemReviewResponse {
//    {
//        "itemId" : 1,
//        "itemName" : "상품명",
//        "categoryName" : "카테고리명",
//        "brandName" : "브랜드명",
//        "thumbnail" : {
//              "imgId" : 1,
//              "url" : "dd"
//          },
//        "reviewList" : [
//              {
//                  "userId" : 1,
//                  "reviewTitle" : "리뷰제목",
//                  "reviewContent" : "리뷰내용",
//                  "reviewStar" : "별점",
//                  "insertDate" : "리뷰등록일일"
//              }
//          ],...
//    }

    private long itemId;    //상품id
    private String categoryName;    //카테고리명
    private String brandName;    //브랜드명
    private String itemName;    //상품명
    private Thumbnail itemThumbnail;   //상품 대표이미지

    private List<ReviewItem> reviewList; //리뷰 리스트

    @Builder
    @Getter
    public static class Thumbnail{
        private long imgId;
        private String url;
    }

    @Builder
    @Getter
    public static class ReviewItem{
        private long userId;
        private String reviewTitle;
        private String reviewContent;
        private int reviewStar;
        private LocalDateTime insertDate;
    }

}