package com.project.shop.item.dto.response;

import com.project.shop.item.domain.Option;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OptionSize {
    private long optionId;
    private String size;

    public static OptionSize of(Option option){
        return OptionSize.builder()
                .optionId(option.getOptionId())
                .size(option.getSize())
                .build();
    }
}
