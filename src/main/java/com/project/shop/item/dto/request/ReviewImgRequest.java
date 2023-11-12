package com.project.shop.item.dto.request;


import com.project.shop.item.domain.ReviewImg;

import javax.validation.constraints.NotBlank;

public record ReviewImgRequest(
        @NotBlank String imgUrl

        ) {
        public ReviewImg toEntity(ReviewImgRequest reviewImgRequest){
                return ReviewImg.builder()
                        .imgUrl(reviewImgRequest.imgUrl)
                        .build();
        }


}
