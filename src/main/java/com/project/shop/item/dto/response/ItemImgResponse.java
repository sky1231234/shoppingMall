package com.project.shop.item.dto.response;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ItemImgResponse {

    private String itemUrl;

    //url 모두 리턴
    public static List<ItemImgResponse> getResponse(Item item) {
        List list = new ArrayList<>();
        for ( ItemImg img : item.getItemImgList() ){
            ItemImgResponse itemImgResponse = ItemImgResponse.builder()
                    .itemUrl(img.getImgUrl())
                    .build();

            list.add(itemImgResponse);
        }
        return list;

    }

}
