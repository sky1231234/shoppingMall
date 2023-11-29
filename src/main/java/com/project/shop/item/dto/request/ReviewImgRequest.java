package com.project.shop.item.dto.request;


import com.project.shop.item.domain.Review;
import com.project.shop.item.domain.ReviewImg;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public record ReviewImgRequest(
        @NotBlank Review review,
        @NotBlank String imgUrl

        ) {
        public ReviewImg toEntity(){
                return ReviewImg.builder()
                        .review(this.getReview())
                        .imgUrl(this.getImgUrl())
                        .build();
        }


}
