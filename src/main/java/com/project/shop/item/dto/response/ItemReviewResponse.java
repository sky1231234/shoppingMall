package com.project.shop.item.dto.response;

import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.Review;
import com.project.shop.item.repository.ItemImgRepository;
import lombok.*;

import java.util.ArrayList;
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
    private ItemImg itemThumbnail;   //상품 대표이미지

    private List<ReviewResponse> reviewList; //리뷰 리스트

    public static ItemReviewResponse fromEntity(ItemImg itemThumbnail, List<Review> reviewList){

        ArrayList<ReviewResponse> reviewEntitylist = new ArrayList<>();

        for (Review review : reviewList) {

            //reviewResponse 자체를 가져오기
            var list = ReviewResponse.fromEntity(review);

            reviewEntitylist.add(list);
        }

        return ItemReviewResponse.builder()
                .itemThumbnail(itemThumbnail)
                .reviewList(reviewEntitylist)
                .build();
    }
}