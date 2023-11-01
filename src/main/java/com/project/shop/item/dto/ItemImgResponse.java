package com.project.shop.item.dto;

import com.project.shop.item.domain.Item;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemImgResponse {

    private String itemUrl;

    //url 모두 리턴
    public static List<ItemImgResponse> getResponse(Item item) {
        List list = new ArrayList<>();
        for ( Img img : item.get )
        return list;

    }

}
