package com.project.shop.item.dto.response;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.Review;
import com.project.shop.user.domain.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserReviewResponse {

    private User user;
    private Item item;
    private String categoryName;
    private String brandName;    //브랜드명
    private String itemName;    //상품명
    private ItemImg itemThumbnail;   //상품 대표이미지

    private UserInReviewResponse reviewList; //리뷰 리스트

      public static UserReviewResponse fromEntity(List<Review> userReviewList){

          ArrayList<UserInReviewResponse> reviewEntitylist = new ArrayList<>();

          for (Review review : userReviewList) {
              var list = UserInReviewResponse.fromEntity(review);
              reviewEntitylist.add(list);
          }

        return UserReviewResponse.builder()
                .categoryName(userReviewList)
                .reviewList()
                .build();
    }
}