package com.project.shop.item.service;

import com.project.shop.item.dto.request.ItemEditRequest;
import com.project.shop.item.dto.request.ItemRequest;
import com.project.shop.item.dto.response.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    //상품 전체 조회
    //ItemResponse 전체가 나와야함
    public ItemResponse itemAllList(){
        ItemResponse itemResponse = null;
        return itemResponse;

    }

    //상품 상세 조회
    //하나의 ItemResponse
    public ItemResponse itemDetailList(int itemId){
        ItemResponse itemResponse = null;
        return itemResponse;
    }

    //상품 등록
    // item + itmeImg + option
    public void itemEnroll(ItemRequest itemRequest){
        itemRequest
    }

    //상품 수정
    public void edit(ItemEditRequest itemEditRequest){

    }

    //상품 삭제
    public void delete(int itemId){

    }
}
