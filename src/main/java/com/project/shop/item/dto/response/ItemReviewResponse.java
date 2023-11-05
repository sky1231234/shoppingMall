package com.project.shop.item.dto.response;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Review;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    private List<ReviewResponse> reviewList; //리뷰 리스트

    //대표 이미지 불러오기
    public static ItemReviewResponse fromEntity(Item item){

        var list = item.getReviewList()
                .stream().map(x -> ReviewResponse.fromEntity(x))
                .collect(Collectors.toList());

        return ItemReviewResponse.builder()
                .categoryName(item.getCategory().getCategoryName())
                .brandName(item.getCategory().getBrandName())
                .itemName(item.getItemName())
                .reviewList(list)
                .build();
    }
}