package com.project.shop.item.dto.request;

import com.project.shop.item.domain.Category;
import com.project.shop.item.domain.Item;
import com.project.shop.item.dto.response.OptionResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public record ItemRequest(
        @NotBlank CategoryRequest categoryRequest,
        @NotBlank String itemName,
        @NotNull int price,
        @NotBlank String explain,

        //service에서 처리
        @NotBlank List<ItemImgRequest> itemImgRequestList,
        @NotBlank List<OptionRequest> optionRequestList
        ) {

        //itemRequest -> item
        public Item toEntity(){

                var imgList = this.getItemImgRequestList()
                        .stream().map(itemImgRequest -> itemImgRequest.toEntity(itemImgRequest))
                        .collect(Collectors.toList());

                var optionList = this.getOptionRequestList()
                        .stream().map(optionRequest -> optionRequest.toEntity(optionRequest))
                        .collect(Collectors.toList());

                return Item.builder()
                        .itemName(this.getItemName())
                        .price(this.getPrice())
                        .explain(this.getExplain())
                        .itemImgList(imgList)
                        .optionList(optionList)
                        .build();
        }
}
