package com.project.shop.item.dto.response;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Option;
import com.project.shop.item.repository.OptionRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Component
public class OptionData {
    private List<OptionSize> optionSizeList;
    private List<OptionColor> optionColorList;

    private OptionRepository optionRepository;

    public OptionData(List<OptionSize> optionSizeList, List<OptionColor> optionColorList){
        this.optionSizeList = optionSizeList;
        this.optionColorList = optionColorList;
    }


    public OptionData ofOptionDataByOption(Item item){

        List<Option> option = this.optionRepository.findByItem(item);

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
