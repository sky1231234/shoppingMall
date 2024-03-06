package com.project.shop.item.dto.request;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Option;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

//option
public record OptionUpdateRequest(
        @NotBlank String size,
        @NotBlank String color
) {

    public Option toEntity(Item item){
        return Option.builder()
                .item(item)
                .color(color)
                .size(size)
                .insertDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }
}
