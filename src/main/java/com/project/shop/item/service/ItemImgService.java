package com.project.shop.item.service;

import com.project.shop.item.domain.ItemImgDomain;
import com.project.shop.item.dto.request.ImgRequest;
import com.project.shop.item.dto.request.ImgUpdateRequest;
import com.project.shop.item.repository.ItemImgRepository;
import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemImgService {

    private final ItemImgRepository itemImgRepository;
    private final ItemImgDomain itemImgDomain;

    @Transactional
    public void createItemImg(List<ImgRequest> itemImgRequestList, Item item){

        List<ItemImg> itemImgList = itemImgDomain.toItemImgList(itemImgRequestList, item);

        itemImgRepository.saveAll(itemImgList);
    }

    public void updateItemImg(Item item, List<ImgUpdateRequest> imgUpdateRequestList){

        deleteItemImgByItemIfNotEmpty(item);

        createItemImgForUpdate(item, imgUpdateRequestList);

    }

    public void deleteItemImgByItemIfNotEmpty(Item item){

        List<ItemImg> itemImgList = item.getItemImgList();
        if (!itemImgList.isEmpty()) {
            itemImgRepository.deleteAll(itemImgList);
        }
    }

    private void createItemImgForUpdate(Item item, List<ImgUpdateRequest> imgUpdateRequestList){

        List<ItemImg> itemImgUpdateList = itemImgDomain.toItemImgListForUpdate(imgUpdateRequestList, item);

        itemImgRepository.saveAll(itemImgUpdateList);
    }

}
