package com.project.shop.item.dto.request;

import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public record ItemUpdateRequest(
        @NotBlank long itemId,
        @NotBlank CategoryUpdateRequest categoryUpdateRequest,
        @NotBlank String itemName,
        @NotBlank int price,
        @NotBlank String explain,

        @NotBlank List<ItemImgUpdateRequest> itemImgUpdateRequestList,
        @NotBlank List<OptionUpdateRequest> optionUpdateRequestList) {

    public Item toEntity(){

//        var imgList = itemUpdateRequest.getItemImgRequestList()
//                .stream().map(itemImgRequest -> itemImgRequest.toEntity(itemImgRequest))
//                .collect(Collectors.toList());
//
//        var optionList = itemUpdateRequest.getOptionRequestList()
//                .stream().map(optionRequest -> optionRequest.toEntity(optionRequest))
//                .collect(Collectors.toList());

        return Item.builder()
                .itemName(this.getItemName())
                .price(this.getPrice())
                .explain(this.getExplain())
                .build();
    }
}
