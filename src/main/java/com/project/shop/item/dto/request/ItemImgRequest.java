package com.project.shop.item.dto.request;

import com.project.shop.item.domain.ItemImg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@Builder
public record ItemImgRequest(
        @NotBlank String imgUrl
        ) {
        public ItemImg toEntity(ItemImgRequest itemImgRequest){
                return ItemImg.builder()
                        .imgUrl(itemImgRequest.getImgUrl())
                        .build();
        }


}
