package com.project.shop.item.domain;

import com.project.shop.item.dto.request.ImgRequest;
import com.project.shop.item.dto.request.ImgUpdateRequest;
import com.project.shop.item.dto.response.ItemImgResponse;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;


@Getter
@Component
public class ItemImgDomain {

    public List<ItemImg> toItemImgList(List<ImgRequest> itemImgRequestList, Item item){
        return itemImgRequestList
                .stream()
                .map(ImgRequest -> ImgRequest.toEntity(item))
                .toList();
    }

    public List<ItemImg> toItemImgListForUpdate(List<ImgUpdateRequest> imgUpdateRequestList, Item item){
        return imgUpdateRequestList
                .stream()
                .map(ImgUpdateRequest -> ImgUpdateRequest.toEntity(item))
                .toList();

    }
    public ItemImgResponse getMainImgByItem(Item item){

        return getItemImgListByItemType(item,ItemImgType.Y)
                .stream().findFirst()
                .orElseThrow(()->new RuntimeException("NOT_FOUND_IMG"));
    }

    public List<ItemImgResponse> getItemImgListByItemType(Item item, ItemImgType itemImgType){

        return item.getItemImgList().stream()
                .filter(itemImg -> itemImg.getItemImgType() == itemImgType)
                .map(ItemImgResponse::of)
                .toList();

    }
}
