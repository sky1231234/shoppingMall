package com.project.shop.item.dto.request;


import com.project.shop.item.domain.Review;
import com.project.shop.item.domain.ReviewImg;
import lombok.Builder;

import javax.validation.constraints.NotBlank;

@Builder
public record ReviewImgRequest(
        @NotBlank String imgUrl

        ) {
        public ReviewImg toEntity(){
                return ReviewImg.builder()
                        .imgUrl(this.imgUrl())
                        .build();
        }

}
