package com.project.shop.item.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class OptionDetails {
    private List<OptionSize> optionSizeList;
    private List<OptionColor> optionColorList;


}
