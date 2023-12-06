package com.project.shop.item.dto.request;

import com.project.shop.item.domain.ItemImg;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
public record ItemImgUpdateRequest(
        @NotNull long itemImgId,
        @NotBlank String imgUrl
        ) {
        public ItemImg toEntity(){
                return ItemImg.builder()
                        .itemImgId(this.itemImgId())
                        .imgUrl(this.imgUrl())
                        .build();
        }


}
