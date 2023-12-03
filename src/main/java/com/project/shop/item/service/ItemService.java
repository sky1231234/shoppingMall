package com.project.shop.item.service;

import com.project.shop.item.domain.*;
import com.project.shop.item.dto.request.*;
import com.project.shop.item.dto.response.ItemListResponse;
import com.project.shop.item.dto.response.ItemResponse;
import com.project.shop.item.dto.response.ItemReviewResponse;
import com.project.shop.item.exception.ItemException;
import com.project.shop.item.repository.CategoryRepository;
import com.project.shop.item.repository.ItemImgRepository;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//import static com.project.shop.global.exception.ErrorCode.NOT_FOUND_ITEM;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final OptionRepository optionRepository;
    private final ItemImgRepository itemImgRepository;

    //상품 전체 조회
    public List<ItemListResponse> itemFindAll(){

        return itemRepository.findAll()
                .stream()
                .map(x -> {
                    return ItemListResponse.builder()
                            .itemId(x.getItemId())
                            .categoryName(x.getCategory().getCategoryName())
                            .brandName(x.getCategory().getBrandName())
                            .itemName(x.getItemName())
                            .itemPrice(x.getPrice())
                            .thumbnail(x.getItemImgList().stream()
                                    .filter(y -> y.getItemImgType() == ItemImgType.Y)
                                    .map(y-> {
                                        return ItemListResponse.Thumbnail.builder()
                                                .imgId(y.getItemImgId())
                                                .url(y.getImgUrl())
                                                .build();
                                    })
                                    .findFirst().orElse(null)
                            ).build();
                })
                .toList();

    }

    //상품 상세 조회
    public ItemResponse itemDetailFind(long itemId){

        Item item = itemRepository.findById(itemId)
                .orElseThrow(()->new RuntimeException("NOT_FOUND_ITEM"));

        var itemImg = item.getItemImgList().stream()
                .filter(y -> y.getItemImgType() == ItemImgType.N)
                .map(y -> {
                    return ItemResponse.ItemImgResponse.builder()
                            .imgId(y.getItemImgId())
                            .url(y.getImgUrl())
                            .build();
                })
                .toList();

        var size = item.getOptionList().stream()
                .map(x -> {
                    return ItemResponse.OptionSize.builder()
                            .optionId(x.getOptionId())
                            .size(x.getSize())
                            .build();
                }).toList();

        var color = item.getOptionList().stream()
                .map(x -> {
                    return ItemResponse.OptionColor.builder()
                            .optionId(x.getOptionId())
                            .color(x.getColor())
                            .build();
                }).toList();

        return ItemResponse.builder()
                .itemId(item.getItemId())
                .categoryName(item.getCategory().getCategoryName())
                .brandName(item.getCategory().getBrandName())
                .itemName(item.getItemName())
                .itemExplain(item.getExplain())
                .itemPrice(item.getPrice())
                .itemThumbnail(item.getItemImgList().stream()
                        .filter(y -> y.getItemImgType() == ItemImgType.Y)
                        .map(y -> {
                            return ItemResponse.ItemImgResponse.builder()
                                    .imgId(y.getItemImgId())
                                    .url(y.getImgUrl())
                                    .build();
                        })
                        .findFirst().orElse(null))
                .itemImgResponseList(itemImg)
                .optionSizeList(size)
                .optionColorList(color)
                .build();
    }

    //상품 등록
    // item + itmeImg + option
    public void create(ItemRequest itemRequest){
        //category
        Category category = categoryRepository
                .findByCategoryNameAndBrandName(
                        itemRequest.getCategoryRequest().getCategoryName(),
                        itemRequest.getCategoryRequest().getBrandName())
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_CATEGORY"));

        //item
        var item = itemRequest.toEntity();
        itemRepository.save(item.updateCategory(category));

        //itemImg
        List<ItemImg> itemImgList = itemRequest
                .getItemImgRequestList()
                .stream()
                .map(ItemImgRequest::toEntity)
                .collect(Collectors.toList());

        for (ItemImg itemImg : itemImgList) {
            itemImg.updateItem(item);
        }

        itemImgRepository.saveAll(itemImgList);

        //option
        List<Option> optionList = itemRequest
                .getOptionRequestList()
                .stream()
                .map(OptionRequest::toEntity)
                .collect(Collectors.toList());

        for (Option option : optionList) {
            option.updateItem(item);
        }

        optionRepository.saveAll(optionList);

    }

    //상품 수정
    //category + item + itemImg + option
    public void update(Long itemId, ItemUpdateRequest itemUpdateRequest){

        Category category = categoryRepository
                        .findByCategoryNameAndBrandName(
                                itemUpdateRequest.getCategoryUpdateRequest().getCategoryName(),
                                itemUpdateRequest.getCategoryUpdateRequest().getBrandName()
                        )
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_CATEGORY"));

        //item
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUND_ITEM"));

        itemRepository.save(item.editItem(category,itemUpdateRequest));

        //itemImg
        //기존 이미지 삭제하고 다시 등록
        List<ItemImg> itemImgList = itemImgRepository.findByItemId(itemId);

        if(itemImgList.isEmpty()){
            throw new RuntimeException("NOT_FOUND_ITEM_IMG");
        }
        itemImgRepository.deleteAll(itemImgList);

        List<ItemImg> itemImgUpdateList = itemUpdateRequest
                .getItemImgUpdateRequestList()
                .stream()
                .map(ItemImgUpdateRequest::toEntity)
                .collect(Collectors.toList());

        for (ItemImg itemImg : itemImgUpdateList) {
            itemImg.updateItem(item);
        }

        itemImgRepository.saveAll(itemImgUpdateList);

    //option

        List<Option> optionUpdateList = itemUpdateRequest
                .optionUpdateRequestList()
                .stream()
                .map(x -> {
                    var option = optionRepository.findByColorAndSize(x.color(), x.size());

                    if(option.isEmpty())
                        x.toEntity();

                    return
                })
                .collect(Collectors.toList());

        optionRepository.saveAll(optionUpdateList);

    }

    //상품 삭제
    public void delete(long itemId){
        itemRepository.deleteById(itemId);
    }
}
