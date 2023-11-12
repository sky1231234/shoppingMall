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

        public Option toEntity(OptionRequest optionRequest){
                return Option.builder()
                        .size(optionRequest.size)
                        .color(optionRequest.color)
                        .build();
        }

}
