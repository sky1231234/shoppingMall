package com.project.shop.item.service;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.dto.request.ItemRequest;
import com.project.shop.item.dto.request.ItemUpdateRequest;
import com.project.shop.item.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemImgService {

    private final ItemImgRepository itemImgRepository;

    @Transactional
    public void createItemImg(ItemRequest itemRequest, Item item){

        List<ItemImg> itemImgList = itemRequest.itemImgRequestList()
                .stream()
                .map(ImgRequest -> ImgRequest.toEntity(item))
                .toList();

        itemImgRepository.saveAll(itemImgList);
    }

    private void createItemImgForUpdate(Item item, ItemUpdateRequest itemUpdateRequest){

        List<ItemImg> itemImgUpdateList = itemUpdateRequest
                .itemImgUpdateRequestList()
                .stream()
                .map(ImgUpdateRequest -> ImgUpdateRequest.toEntity(item))
                .toList();

        itemImgRepository.saveAll(itemImgUpdateList);
    }

    public void updateItemImg(Item item, ItemUpdateRequest itemUpdateRequest){

        //기존 이미지 삭제 후 등록
        deleteItemImgByItemIfNotEmpty(item);

        //고치기 : createItemImg와 메소드 합치고싶음
        createItemImgForUpdate(item, itemUpdateRequest);

    }

    public void deleteItemImgByItemIfNotEmpty(Item item){

        List<ItemImg> itemImgList = item.getItemImgList();
        if (!itemImgList.isEmpty()) {
            itemImgRepository.deleteAll(itemImgList);
        }
    }
}
