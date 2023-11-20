package com.project.shop.item.dto.response;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ItemImgResponse {

    private String itemUrl;

    public static List<ItemImgResponse> toResponse(Item item) {
        List<ItemImgResponse> list = new ArrayList<>();
        for (ItemImg img : item.get()) {
            ItemImgResponse imageResponse = ItemImgResponse.builder().fileUrl(image.getFileUrl()).build();
            list.add(imageResponse);
        }
        return list;
    }

}
