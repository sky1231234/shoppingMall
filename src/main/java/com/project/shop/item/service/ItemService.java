package com.project.shop.item.service;

import com.project.shop.item.domain.*;
import com.project.shop.item.dto.request.*;
import com.project.shop.item.dto.response.*;
import com.project.shop.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;


    private final ItemDomain itemDomain;
    private final ItemImgDomain itemImgDomain;
    private final OptionData optionData;

    private final CategoryService categoryService;
    private final ItemImgService itemImgService;
    private final OptionService optionService;

    public List<ItemListResponse> findAll(){

        return itemRepository.findAll().stream()
                .map(item -> {

                    ItemImgResponse thumbnailItemImg = itemImgDomain.getMainImgByItem(item);

                    return ItemListResponse.of(item, thumbnailItemImg);
                    }
                )
                .toList();

    }

    public ItemResponse findItemDetailInfo(long itemId){

        Item item = getItemById(itemId);

        ItemImgResponse ItemMainImgResponses = itemImgDomain.getMainImgByItem(item);

        List<ItemImgResponse> itemImgResponses = itemImgDomain.getItemImgListByItemType(item, ItemImgType.N);

        OptionData optionDataResponse = optionData.ofOptionDataByOption(item);

        return ofItemResponse(item, ItemMainImgResponses, itemImgResponses, optionDataResponse);
    }


    private ItemResponse ofItemResponse(Item item, ItemImgResponse thumbnailItemImg, List<ItemImgResponse> itemImg, OptionData optionData) {

        return ItemResponse.of(item,
                thumbnailItemImg,
                itemImg,
                optionData.getOptionSizeList(),
                optionData.getOptionColorList());
    }

    @Transactional
    public long create(ItemRequest itemRequest){

        Category category = categoryService.findCategoryByCategoryData(
                itemRequest.categoryRequest().categoryName(),
                itemRequest.categoryRequest().brandName());

        Item toItem = itemDomain.toItem(category, itemRequest);

        return saveItemAndImgAndOption(toItem, itemRequest.itemImgRequestList(), itemRequest.optionRequestList());
    }

    @Transactional
    public long saveItemAndImgAndOption(Item toItem, List<ImgRequest> itemImgRequestList, List<OptionRequest> optionRequestList){

        Item createItem = createItem(toItem);

        itemImgService.createItemImg(itemImgRequestList, toItem);
        optionService.createOption(optionRequestList, toItem);

        return createItem.getItemId();
    }

    @Transactional
    private Item createItem(Item item) {
        return itemRepository.save(item);
    }


    @Transactional
    public void update(long itemId, ItemUpdateRequest itemUpdateRequest){

        Category category = categoryService.findCategoryByCategoryData(
                itemUpdateRequest.categoryUpdateRequest().categoryName(),
                itemUpdateRequest.categoryUpdateRequest().brandName());

        //dont ask ??
        Item item = getItemById(itemId);
        updateItemAndImgAndOption(category, item, itemUpdateRequest);
    }

    @Transactional
    private Item getItemById(long itemId){
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ITEM"));
    }

    @Transactional
    public void updateItemAndImgAndOption(Category category, Item toItem, ItemUpdateRequest itemUpdateRequest){

        toItem.updateItemInfo(category, itemUpdateRequest.itemName(), itemUpdateRequest.price(), itemUpdateRequest.explain());
        itemImgService.updateItemImg(toItem, itemUpdateRequest.itemImgUpdateRequestList());
        optionService.updateItemOption(toItem, itemUpdateRequest.optionUpdateRequestList());

    }

    @Transactional
    public void delete(long itemId){

        Item item = getItemById(itemId);

        deleteItemById(itemId);
        deleteItemData(item);
    }

    @Transactional
    private void deleteItemById(long itemId){
        itemRepository.deleteById(itemId);
    }

    @Transactional
    private void deleteItemData(Item item){
        itemImgService.deleteItemImgByItemIfNotEmpty(item);
        optionService.deleteItemOptionByItemIfNotEmpty(item);
    }


}
