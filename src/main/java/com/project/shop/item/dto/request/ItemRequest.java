package com.project.shop.item.dto.request;

import com.project.shop.item.domain.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public record ItemRequest(
        @NotBlank CategoryRequest categoryRequest,
        @NotBlank String itemName,
        @NotNull int price,
        @NotBlank String explain,

        @NotBlank List<ImgRequest> itemImgRequestList,
        @NotBlank List<OptionRequest> optionRequestList
        ) {

        public Item toEntity(Category category){
                return Item.builder()
                        .category(category)
                        .itemName(this.itemName())
                        .price(this.price())
                        .explain(this.explain())
                        .insertDate(LocalDateTime.now())
                        .updateDate(LocalDateTime.now())
                        .build();
        }

}
