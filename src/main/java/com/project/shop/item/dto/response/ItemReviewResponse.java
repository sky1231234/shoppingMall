package com.project.shop.item.dto.response;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Review;
import com.project.shop.item.repository.ItemImgMapping;
import com.project.shop.item.repository.ItemImgRepository;
import com.project.shop.item.repository.ItemRepository;
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
    private List<ItemImgMapping> itemThumbnail;   //상품 대표이미지

    private List<ReviewResponse> reviewList; //리뷰 리스트


    private static ItemImgRepository itemImgRepository;

    public static ItemReviewResponse fromEntity(Item item){

        //findByMainUrl 하는 위치 맞나?
        //썸네일 url 한개여야하는데 받아오는 값이 list임
        var thumbnail = itemImgRepository.findByItemIdAndMainImg(item.getItemId(),"Y");

        var list = item.getReviewList()
                .stream().map(x -> ReviewResponse.fromEntity(x))
                .collect(Collectors.toList());

        return ItemReviewResponse.builder()
                .categoryName(item.getCategory().getCategoryName())
                .brandName(item.getCategory().getBrandName())
                .itemName(item.getItemName())
                .itemThumbnail(thumbnail)
                .reviewList(list)
                .build();
    }
}