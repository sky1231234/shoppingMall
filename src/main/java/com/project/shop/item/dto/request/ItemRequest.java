package com.project.shop.item.dto.request;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Review;
import com.project.shop.item.dto.response.ItemImgResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public record ItemRequest(
        @NotBlank String categoryName,
        @NotBlank String brandName,
        @NotBlank String itemName,
        @NotBlank String price,
        @NotBlank String explain,
        @NotBlank List<ItemImgEnrollRequest> itemImgEnrollRequestList,
        @NotBlank List<OptionEnrollRequest> optionEnrollRequestList
        ) {

        //itemRequest를 item으로 바꿔야함
        public Item toEntity(){

                var imgList = ItemRequest.get()
                        .stream().map(x -> new ItemImgResponse(x.getImgUrl()))
                        .collect(Collectors.toList());

                return Item.builder()
                        .categoryName(categoryName)
                        .brandName(brandName)
                        .itemName(itemName)
                        .price(price)
                        .explain(explain)
                        .itemImgEnrollRequestList(itemImgEnrollRequestList)
                        .optionEnrollRequestList(optionEnrollRequestList)
                        .build();
        }
}
