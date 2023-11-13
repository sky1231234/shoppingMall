package com.project.shop.item.dto.request;

import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.Option;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public record OptionRequest(
        @NotBlank String size,
        @NotBlank String color
        ) {

        public Option toEntity(){
                return Option.builder()
                        .size(this.size)
                        .color(this.color)
                        .build();
        }

}
