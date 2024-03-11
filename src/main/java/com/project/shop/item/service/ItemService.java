package com.project.shop.item.service;

import com.project.shop.item.domain.*;
import com.project.shop.item.dto.request.*;
import com.project.shop.item.dto.response.*;
import com.project.shop.item.repository.*;
import com.project.shop.member.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final OptionRepository optionRepository;

    private final AuthService authService;
    private final CategoryService categoryService;
    private final ItemImgService itemImgService;
    private final OptionService optionService;

    //상품 전체 조회
    public List<ItemListResponse> findAll(){

        return itemRepository.findAll().stream()
                .map(item -> {

                    ItemImgResponse thumbnailItemImg = getMainImgByItem(item);

                    return ItemListResponse.of(item, thumbnailItemImg);
                    }
                )
                .toList();

    }

    //상품 상세 조회
    //메소드명
    public ItemResponse findItemDetailInfo(long itemId){

        Item item = getItemById(itemId);

        ItemImgResponse thumbnailItemImg = getMainImgByItem(item);

        List<ItemImgResponse> itemImg = getItemImgByItemType(item, ItemImgType.Y);

        OptionDetails optionDetails = getOptionByItem(item);

        return returnItemResponse(item, thumbnailItemImg, itemImg, optionDetails);
    }

    private ItemResponse returnItemResponse(Item item, ItemImgResponse thumbnailItemImg, List<ItemImgResponse> itemImg, OptionDetails optionDetails) {

        return ItemResponse.of(item,
                thumbnailItemImg,
                itemImg,
                optionDetails.getOptionSizeList(),
                optionDetails.getOptionColorList());
    }

    //상품 등록
    // item + itemImg + option
    @Transactional
    public long create(String loginId, ItemRequest itemRequest){

        checkUserPermission(loginId);

        Category category = categoryService.getCategory(
                itemRequest.categoryRequest().categoryName(),
                itemRequest.categoryRequest().brandName());

        Item item = toItem(category, itemRequest);
        Item createItem = createItem(item);

        itemImgService.createItemImg(itemRequest, item);
        optionService.createOption(itemRequest, item);

        return createItem.getItemId();
    }

    //상품 수정
    //category + item + itemImg + option
    @Transactional
    public void update(String loginId, long itemId, ItemUpdateRequest itemUpdateRequest){

        checkUserPermission(loginId);

        Category category = categoryService.getCategory(
                itemUpdateRequest.categoryUpdateRequest().categoryName(),
                itemUpdateRequest.categoryUpdateRequest().brandName());

        //item
        Item item = getItemById(itemId);
        updateItem(itemId, itemUpdateRequest, category);

        itemImgService.updateItemImg(item, itemUpdateRequest);
        optionService.updateItemOption(item,itemUpdateRequest);
    }

    //상품 삭제
    @Transactional
    public void delete(String loginId, long itemId){

        checkUserPermission(loginId);

        Item item = getItemById(itemId);

        deleteItemById(itemId);
        deleteItemData(item);
    }

    private Item toItem(Category category,ItemRequest itemRequest) {
        return itemRequest.toEntity(category);
    }

    @Transactional
    private Item createItem(Item item) {
        return itemRepository.save(item);
    }

    private void checkUserPermission(String loginId) {
        authService.authCheck(loginId);
    }

    private void updateItem(long itemId, ItemUpdateRequest itemUpdateRequest, Category category){
        Item item = getItemById(itemId);

        item.updateItemInfo(category,
                itemUpdateRequest.itemName(),
                itemUpdateRequest.price(),
                itemUpdateRequest.explain());
    }

    private Item getItemById(long itemId){
         return itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ITEM"));
    }

    private void deleteItemById(long itemId){
        itemRepository.deleteById(itemId);
    }

    private void deleteItemData(Item item){
        itemImgService.deleteItemImgByItemIfNotEmpty(item);
        optionService.deleteItemOptionByItemIfNotEmpty(item);
    }


    //메인 이미지와 나머지 이미지를 가져오는 부분이 거의 비슷해서
//    1번과
    private ItemImgResponse getMainImg(Item item){

        return item.getItemImgList().stream()
                .filter(itemImg -> itemImg.getItemImgType() == ItemImgType.Y)
                .findFirst()
                .map(ItemImgResponse::of)
                .orElseThrow(()->new RuntimeException("NOT_FOUND_IMG"));

    }
//    2번을
    private List<ItemImgResponse> getImgByItem(Item item){

        return item.getItemImgList().stream()
                .filter(itemImg -> itemImg.getItemImgType() == ItemImgType.N)
                .map(ItemImgResponse::of)
                .toList();
    }

//    합침
    private List<ItemImgResponse> getItemImgByItemType(Item item, ItemImgType itemImgType){

        return item.getItemImgList().stream()
                .filter(itemImg -> itemImg.getItemImgType() == itemImgType)
                .map(ItemImgResponse::of)
                .toList();

    }

    private ItemImgResponse getMainImgByItem(Item item){

        return getItemImgByItemType(item,ItemImgType.Y)
                .stream().findFirst()
                .orElseThrow(()->new RuntimeException("NOT_FOUND_IMG"));
    }

    private OptionDetails getOptionByItem(Item item){

        List<Option> option = optionRepository.findByItem(item);

        List<OptionSize> size = getOptionSizeByItem(option);
        List<OptionColor> color = getOptionColorByItem(option);

        return new OptionDetails(size, color);
    }

    private List<OptionSize> getOptionSizeByItem(List<Option> option){

         return option.stream()
                .map(OptionSize::of).toList();
    }

    private List<OptionColor> getOptionColorByItem(List<Option> option){

        return option.stream()
                .map(OptionColor::of).toList();
    }
}
