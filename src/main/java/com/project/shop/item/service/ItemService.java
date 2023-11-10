package com.project.shop.item.service;

import com.project.shop.item.domain.Item;
import com.project.shop.item.dto.request.ItemEditRequest;
import com.project.shop.item.dto.request.ItemRequest;
import com.project.shop.item.dto.response.ItemResponse;
import com.project.shop.item.dto.response.ReviewResponse;
import com.project.shop.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    //상품 전체 조회
    public List<ItemResponse> itemfindAllList(){

        List<ItemResponse> itemResponse = itemRepository.findAll().stream()
                .map(item -> new ItemResponse()).collect(Collectors.toList());

        return itemResponse;

    }

    //상품 상세 조회
    public ItemResponse itemDetailList(long itemId){

        var review = itemRepository.findById(itemId)
                .orElseThrow(()->new RuntimeException("itemID가 없습니다."));

        return ItemResponse.fromEntity(review);
    }

    //상품 등록
    // item + itmeImg + option
    public void itemEnroll(ItemRequest itemRequest){
        //수정 toEntity
        itemRepository.save(itemRequest.toEntity());

    }

    //상품 수정
    public void edit(ItemEditRequest itemEditRequest){
        //수정 toEntity
        itemRepository.save(itemEditRequest.toEntity());
    }

    //상품 삭제
    public void delete(long itemId){
        itemRepository.deleteById(itemId);
    }
}
