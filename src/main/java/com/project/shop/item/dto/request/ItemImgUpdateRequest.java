package com.project.shop.item.dto.request;

import com.project.shop.item.domain.ItemImg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@Builder
public record ItemImgUpdateRequest(
        @NotBlank String imgUrl
        ) {
        public ItemImg toEntity(){
                return ItemImg.builder()
                        .imgUrl(this.getImgUrl())
                        .build();
        }


}
