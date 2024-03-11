package com.project.shop.item.dto.request;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.ItemImgType;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public record ImgRequest(
        @NotBlank ItemImgType mainImg,
        @NotBlank String url
) {

    public ItemImg toEntity(Item item){

        return ItemImg.builder()
                .item(item)
                .imgUrl(url)
                .itemImgType(mainImg)
                .dateTime(LocalDateTime.now())
                .build();
    }

}
