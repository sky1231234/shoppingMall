package com.project.shop.item.dto.request;

import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.Review;
import com.project.shop.item.domain.ReviewImg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@Builder
public record ReviewImgUpdateRequest(
        @NotBlank String imgUrl
        ) {

        public ReviewImg toEntity(){
                return ReviewImg.builder()
                        .imgUrl(this.getImgUrl())
                        .build();
        }


}
