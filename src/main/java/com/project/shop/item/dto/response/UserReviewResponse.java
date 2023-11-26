package com.project.shop.item.dto.response;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.Review;
import com.project.shop.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserReviewResponse {

//    {
//        "userId" : 1,
//            "reviewItemList" : [
        //        {
        //            "itemId" : 1,
        //                "itemName" : "상품명",
        //                "categoryName" : "카테고리명",
        //                "brandName" : "브랜드명",
        //                "thumbnail" : {
        //                      "imgId" : 1,
        //                      "url" : "dd"
        //        },
        //            "reviewTitle" : "리뷰제목",
        //                "reviewContent" : "리뷰내용",
        //                "reviewStar" : "별점",
        //                "insertDate" : "리뷰등록일일"
        //        }
//      ]
//    }

    private long userId;
    private List<ReviewItem> reviewItemList;

    @Builder
    public static class ReviewItem{
      private long itemId;
        private String categoryName;
        private String brandName;    //브랜드명
        private String itemName;    //상품명
        private Thumbnail itemThumbnail;   //상품 대표이미지
        private String reviewTitle;
        private String reviewContent;
        private int reviewStar;
        private LocalDateTime insertDate;

        @Builder
        public static class Thumbnail{
            private long imgId;
            private String url;
        }

    }


}