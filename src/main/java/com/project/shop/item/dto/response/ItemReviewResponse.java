package com.project.shop.item.dto.response;

import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
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
    private String brandName;    //브랜드명

    private String itemName;    //상품명
    //수정 itemimg아니고 string
    private ItemImg itemThumbnail;   //상품 대표이미지

    private List<ItemInReviewResponse> reviewList; //리뷰 리스트

    public static ItemReviewResponse fromEntity(Item item, ItemImg itemThumbnail, List<Review> reviewList){

        ArrayList<ItemInReviewResponse> reviewEntitylist = new ArrayList<>();

        for (Review review : reviewList) {
            //reviewResponse 자체를 가져오기
            var list = ItemInReviewResponse.fromEntity(review);
            reviewEntitylist.add(list);
        }

        return ItemReviewResponse.builder()
                .categoryName(item.getCategory().getCategoryName())
                .brandName(item.getCategory().getBrandName())
                .itemThumbnail(itemThumbnail)
                .reviewList(reviewEntitylist)
                .build();
    }
}