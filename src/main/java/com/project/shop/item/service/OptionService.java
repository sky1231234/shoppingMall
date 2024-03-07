package com.project.shop.item.service;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.Option;
import com.project.shop.item.dto.request.ItemRequest;
import com.project.shop.item.dto.request.ItemUpdateRequest;
import com.project.shop.item.repository.ItemImgRepository;
import com.project.shop.item.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OptionService {

    private final OptionRepository optionRepository;

    @Transactional
    public void createOption(ItemRequest itemRequest, Item item){

        List<Option> optionList = itemRequest.optionRequestList()
                .stream()
                .map(OptionRequest -> OptionRequest.toEntity(item))
                .toList();

        optionRepository.saveAll(optionList);
    }

    public void updateItemOption(Item item, ItemUpdateRequest itemUpdateRequest){

        deleteItemOptionByItemIfNotEmpty(item);
        createItemOptionForUpdate(item, itemUpdateRequest);

    }

    public void deleteItemOptionByItemIfNotEmpty(Item item){
        List<Option> optionList = optionRepository.findByItem(item);
        if (!optionList.isEmpty()) {
            optionRepository.deleteAll(optionList);
        }
    }

    private void createItemOptionForUpdate(Item item, ItemUpdateRequest itemUpdateRequest){

        List<Option> optionUpdateList = itemUpdateRequest
                .optionUpdateRequestList()
                .stream()
                .filter(option -> optionRepository.findByItemAndColorAndSize(item,option.color(), option.size()).isEmpty())
                .map(OptionUpdateRequest -> OptionUpdateRequest.toEntity(item))
                .collect(Collectors.toList());

        optionRepository.saveAll(optionUpdateList);

    }
}
