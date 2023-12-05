package com.project.shop.item.dto.request;

import com.project.shop.item.domain.ItemImg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@Builder
public record ItemImgRequest(
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
