package com.project.shop.item.dto.response;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Thumbnail {
    private long itemImgId;
    private String imgUrl;

    public static Thumbnail of(ItemImg mainItemImg){

        return new Thumbnail(
                        mainItemImg.getItemImgId(),
                        mainItemImg.getImgUrl()
        );
    }
}
