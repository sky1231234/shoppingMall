package com.project.shop.item.dto.response;

import com.project.shop.item.domain.Option;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OptionColor {
    private long optionId;
    private String color;

    public static OptionColor of(Option option){
        return OptionColor.builder()
                .optionId(option.getOptionId())
                .color(option.getColor())
                .build();
    }
}
