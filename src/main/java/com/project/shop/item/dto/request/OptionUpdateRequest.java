package com.project.shop.item.dto.request;

import com.project.shop.item.domain.Option;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public record OptionUpdateRequest(
        @NotBlank String color,
        @NotBlank String size
        ) {

        public Option toEntity(){
                return Option.builder()
                        .color(this.color)
                        .size(this.size)
                        .build();
        }

}
