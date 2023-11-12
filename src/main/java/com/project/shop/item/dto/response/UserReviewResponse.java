package com.project.shop.item.dto.response;

import com.project.shop.item.domain.Review;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserReviewResponse {

    private ItemReviewResponse itemReviewResponseList;

      public static UserReviewResponse fromEntity(List<Review> userReviewList){

          //전체 수정
          //userReviewList 중에 한 리뷰에 대한 상품+리뷰를 포함한게 userReview
          for (Review userReview : userReviewList) {

              //list아니고 ItemReviewResponse로 리턴받음
              var list = ItemReviewResponse.fromEntity(userReview);

          }

        return UserReviewResponse.builder()
                .itemReviewResponseList(list)
                .build();
    }
}