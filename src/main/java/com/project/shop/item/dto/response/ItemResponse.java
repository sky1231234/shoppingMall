package com.project.shop.item.dto.response;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.Review;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponse {

    private String categoryName;
    private String brandName;
    private String itemName;
    private int itemPrice;
    private String itemExplain;
    private List<ItemImgResponse> itemImgResponseList;
    private List<OptionResponse> optionResponseList;

    public static ItemResponse fromEntity(Item item) {

        var imgList = item.getItemImgList()
                .stream().map(x -> new ItemImgResponse(x.getImgUrl()))
                .collect(Collectors.toList());

        var optionList = item.getOptionList()
                .stream().map(x -> new OptionResponse(x.getSize(), x.getColor()))
                .collect(Collectors.toList());

        return ItemResponse.builder()
                .categoryName(item.getCategory().getCategoryName())
                .brandName(item.getCategory().getBrandName())
                .itemName(item.getItemName())
                .itemPrice(item.getPrice())
                .itemExplain(item.getExplain())
                .itemImgResponseList(imgList)
                .optionResponseList(optionList)
                .build();
    }
}