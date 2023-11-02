package com.project.shop.item.service;

import com.project.shop.item.dto.request.ItemEditRequest;
import com.project.shop.item.dto.request.ItemRequest;
import com.project.shop.item.dto.request.ReviewEditRequest;
import com.project.shop.item.dto.request.ReviewRequest;
import com.project.shop.item.dto.response.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {


    //리뷰 전체 조회
    //ItemResponse 전체가 나와야함
    public ItemResponse itemAllList(){
        ItemResponse itemResponse = null;
        return itemResponse;

    }

    //리뷰 상세 조회
    public ItemResponse itemDetailList(int itemId){
        ItemResponse itemResponse = null;
        return itemResponse;
    }

    //리뷰 등록
    public void reviewEnroll(ReviewRequest reviewRequest){

    }

    //리뷰 수정
    public void edit(ReviewEditRequest reviewEditRequest){

    }

    //리뷰 삭제
    public void delete(int reviewId){

    }
}
