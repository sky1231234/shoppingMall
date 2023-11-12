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
        @NotBlank String categoryName,
        @NotBlank String brandName,
        @NotBlank Category category,
        @NotBlank String itemName,
        @NotBlank int price,
        @NotBlank String explain,
        @NotBlank List<ItemImgRequest> itemImgRequestList,
        @NotBlank List<OptionRequest> optionRequestList) {

    public Item toEntity(ItemUpdateRequest itemUpdateRequest){

        var imgList = itemUpdateRequest.getItemImgRequestList()
                .stream().map(itemImgRequest -> itemImgRequest.toEntity(itemImgRequest))
                .collect(Collectors.toList());

        var optionList = itemUpdateRequest.getOptionRequestList()
                .stream().map(optionRequest -> optionRequest.toEntity(optionRequest))
                .collect(Collectors.toList());

        return Item.builder()
                .category(category)
                .itemName(itemName)
                .price(price)
                .explain(explain)
                .itemImgList(imgList)
                .optionList(optionList)
                .build();
    }
}
