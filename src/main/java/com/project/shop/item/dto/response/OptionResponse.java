package com.project.shop.item.dto.response;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Option;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OptionResponse {

    private String size;
    private String color;

    public static List<OptionResponse> getResponse(Item item) {
        List list = new ArrayList<>();
        for (Option option : item.getOptionList()) {
            OptionResponse optionResponse = OptionResponse.builder()
                    .size(option.getSize())
                    .color(option.getColor())
                    .build();

            list.add(optionResponse);
        }
        return list;
    }
}