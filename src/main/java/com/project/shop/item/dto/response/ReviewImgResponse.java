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
public class ReviewImgResponse {

    private String itemUrl;

    //url 모두 리턴
    public static List<ReviewImgResponse> getResponse(Item item) {
        List list = new ArrayList<>();
        for ( ItemImg img : item.getItemImgList() ){
            ReviewImgResponse itemImgResponse = ReviewImgResponse.builder()
                    .itemUrl(img.getImgUrl())
                    .build();

            list.add(itemImgResponse);
        }
        return list;

    }

}
