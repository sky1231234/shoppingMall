package com.project.shop.item.domain;

import com.project.shop.item.dto.request.OptionRequest;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;


@Getter
@Component
public class OptionDomain {

    public List<Option> toOptionList(List<OptionRequest> optionRequestList, Item item){
        return optionRequestList
                .stream()
                .map(OptionRequest -> OptionRequest.toEntity(item))
                .toList();
    }


}
