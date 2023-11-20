package com.project.shop.item.service;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.Option;
import com.project.shop.item.dto.request.ItemImgRequest;
import com.project.shop.item.dto.request.ItemUpdateRequest;
import com.project.shop.item.dto.request.ItemRequest;
import com.project.shop.item.dto.request.OptionRequest;
import com.project.shop.item.dto.response.ItemResponse;
import com.project.shop.item.exception.ItemException;
import com.project.shop.item.repository.CategoryRepository;
import com.project.shop.item.repository.ItemImgRepository;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.shop.global.exception.ErrorCode.NOT_FOUND_ITEM;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final OptionRepository optionRepository;
    private final ItemImgRepository itemImgRepository;

    //상품 전체 조회
    public List<ItemResponse> itemFindAll(){

        return itemRepository.findAll()
                .stream()
                .map(ItemResponse::fromEntity)
                .collect(Collectors.toList());

    }

    //상품 상세 조회
    public ItemResponse itemDetailFind(long itemId){

        var item = itemRepository.findById(itemId)
                .orElseThrow(()->new ItemException(NOT_FOUND_ITEM));

        return ItemResponse.fromEntity(item);
    }

    //상품 등록
    // item + itmeImg + option
    public void create(ItemRequest itemRequest){
        //category
        var category = categoryRepository
                .findByCategoryNameAndBrandName(itemRequest.getCategoryRequest().getCategoryName(),itemRequest.getCategoryRequest().getBrandName());

        if(category.isEmpty()){
            throw new RuntimeException("NOT_FOUND_CATEGORY");
        }

        //item
        var item = itemRequest.toEntity();
        item.updateCategory(category.get());
        itemRepository.save(item);

        //수정 itemId는 어떻게 저장되는거지?
        //itemImg
        List<ItemImg> itemImgList = itemRequest
                .getItemImgRequestList()
                .stream()
                .map(ItemImgRequest::toEntity)
                .toList();

//        for (ItemImg itemImg : itemImgList) {
//            itemImg.updateItem(item);
//        }

        itemImgRepository.saveAll(itemImgList);

        //optiton
        List<Option> optionList = itemRequest
                .getOptionRequestList()
                .stream()
                .map(OptionRequest::toEntity)
                .toList();

        for (Option option : optionList) {
            option.updateItem(item);
        }

        optionRepository.saveAll(optionList);



    }

    //상품 수정
    public void update(Long itemId, ItemUpdateRequest itemUpdateRequest){
//        기존꺼 삭제하고 수정?
//        itemRepository.save(itemUpdateRequest.toEntity(itemUpdateRequest));
    }

    //상품 삭제
    public void delete(long itemId){
        itemRepository.deleteById(itemId);
    }
}
