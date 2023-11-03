package com.project.shop.item.dto.response;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.Review;
import com.project.shop.item.domain.ReviewImg;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReviewImgResponse {

    private String itemUrl;

    //url 모두 리턴
    public static List<ReviewImgResponse> getResponse(Review review) {
        List list = new ArrayList<>();
        for ( ReviewImg img : review.getReviewImgList() ){
            ReviewImgResponse itemImgResponse = ReviewImgResponse.builder()
                    .itemUrl(img.getImgUrl())
                    .build();

            list.add(itemImgResponse);
        }
        return list;

    }

    //대표이미지 불러오는 함수
    public static String getMainUrl(int itemId){
        return "mainUrl";
    }

}
