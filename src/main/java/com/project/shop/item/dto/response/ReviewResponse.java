package com.project.shop.item.dto.response;

import com.project.shop.item.domain.Item;
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
//    {
//        "itemId" : 1,
//        "itemName" : "상품명",
//        "categoryName" : "카테고리명",
//        "brandName" : "브랜드명",
//        "thumbnail" : {
//              "imgId" : 1,
//              "url" : "dd"
//          },
//        "userId" : 1,
//        "reviewTitle" : "리뷰제목",
//        "reviewContent" : "리뷰내용",
//        "reviewStar" : "별점",
//        "reviewImg" : [
//              {
//                  "imgId" : 1,
//                  "url" "dd"
//              }
//        ]
//        "insertDate" : "리뷰등록일"
//      }

    private long itemId;
    private String categoryName;    //카테고리명
    private String brandName;       //브랜드명
    private String itemName;    //상품명
    private Thumbnail thumbnail; //상품 대표 이미지

    private long userId; //회원id
    private String reviewTitle;   //리뷰 제목
    private String reviewContent; //리뷰 내용
    private int reviewStar;    //별점
    private List<ReviewImg> reviewImgResponses; //리뷰 이미지
    private LocalDateTime insertDate;   //리뷰 등록일

    @Builder
    public static class Thumbnail{
        private long imgId;
        private String url;
    }
    @Builder
    public static class ReviewImg{
        private long imgId;
        private String url;
    }

}