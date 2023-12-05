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

        return Item.builder()
                .itemName(this.itemName())
                .price(this.price())
                .explain(this.explain())
                .build();
    }
}
