package com.project.shop.item.service;

import com.project.shop.item.dto.request.ItemRequest;
import com.project.shop.item.dto.request.ItemUpdateRequest;
import com.project.shop.item.dto.request.OptionRequest;
import com.project.shop.item.dto.request.OptionUpdateRequest;
import com.project.shop.item.repository.OptionRepository;
import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final Option option;

    @Transactional
    public void createOption(List<OptionRequest> optionRequestList, Item item){

        List<Option> optionList = option.toOptionList(optionRequestList, item);

        optionRepository.saveAll(optionList);
    }

    public void updateItemOption(Item item, List<OptionUpdateRequest> optionUpdateRequestList){

        deleteItemOptionByItemIfNotEmpty(item);
        createItemOptionForUpdate(item, optionUpdateRequestList);

    }

    public void deleteItemOptionByItemIfNotEmpty(Item item){
        List<Option> optionList = optionRepository.findByItem(item);
        if (!optionList.isEmpty()) {
            optionRepository.deleteAll(optionList);
        }
    }

    private void createItemOptionForUpdate(Item item, List<OptionUpdateRequest> optionUpdateRequestList){

        List<Option> optionUpdateList = toOptionListForUpdate(optionUpdateRequestList, item);

        optionRepository.saveAll(optionUpdateList);

    }

    public List<Option> toOptionListForUpdate(List<OptionUpdateRequest> optionUpdateRequestList, Item item){
        return optionUpdateRequestList
                .stream()
                .filter(option -> optionRepository.findByItemAndColorAndSize(item,option.color(), option.size()).isEmpty())
                .map(OptionUpdateRequest -> OptionUpdateRequest.toEntity(item))
                .collect(Collectors.toList());
    }

}
