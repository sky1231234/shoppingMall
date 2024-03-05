package com.project.shop.item.dto.response;

import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.Option;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ItemImgResponse {
    private long imgId;
    private String url;

    public static ItemImgResponse of(ItemImg itemImg){
        return ItemImgResponse.builder()
                .imgId(itemImg.getItemImgId())
                .url(itemImg.getImgUrl())
                .build();
    }
}
