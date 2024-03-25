package com.project.shop.item.dto.response;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Option;
import com.project.shop.item.repository.OptionRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class OptionData {
    private List<OptionSize> optionSizeList;
    private List<OptionColor> optionColorList;

    public OptionData(List<OptionSize> optionSizeList, List<OptionColor> optionColorList){
        this.optionSizeList = optionSizeList;
        this.optionColorList = optionColorList;
    }

    private OptionRepository optionRepository;

    public OptionData ofOptionDataByOption(Item item){

        List<Option> option = optionRepository.findByItem(item);

        List<OptionSize> size = ofOptionSizeListByItem(option);
        List<OptionColor> color = ofOptionColorListByItem(option);

        return new OptionData(size, color);
    }

    private List<OptionSize> ofOptionSizeListByItem(List<Option> option){

        return option.stream()
                .map(OptionSize::of).toList();
    }

    private List<OptionColor> ofOptionColorListByItem(List<Option> option){

        return option.stream()
                .map(OptionColor::of).toList();
    }


}
