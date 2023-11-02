package com.project.shop.item.dto.response;

import com.project.shop.item.domain.Item;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {
    private String categoryName;
    private String brandName;
    private String itemName;
    private String itemThumbnail;
    private int userId;
    private String title;
    private String content;
    private String star;
    private List<ReviewImgResponse> reviewImgResponses;
    private LocalDateTime insertDate;

        public static ReviewResponse getResponse(Item item){
            return ReviewResponse.builder()
                .categoryName(item.getCategory().getCategoryName())
                .brandName(item.getCategory().getBrandName())
                .itemName(item.getItemName())
                .itemThumbnail()
                .userId()
                .title()
                .content()
                .star()
                .reviewImgResponses(ReviewImgResponse.getResponse(item))
                .insertDate()
                .build();
    }
}