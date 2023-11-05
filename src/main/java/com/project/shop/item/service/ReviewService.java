package com.project.shop.item.service;

import com.project.shop.item.domain.Item;
import com.project.shop.item.dto.request.ReviewEditRequest;
import com.project.shop.item.dto.request.ReviewRequest;
import com.project.shop.item.dto.response.ItemReviewResponse;
import com.project.shop.item.dto.response.ReviewResponse;
import com.project.shop.item.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    //상품 - 리뷰 조회
    public ItemReviewResponse itemReviewList(long itemId){
        var itemID = reviewRepository.findById(itemId);
        ItemReviewResponse itemReviewResponse = itemID.get().;
        return itemReviewResponse;

    }

    //회원 - 리뷰 조회
    public ItemReviewResponse userReviewList(int userId){
        ItemReviewResponse itemReviewResponse = null;
        return itemReviewResponse;

    }

    //리뷰 상세 조회
    public ReviewResponse reviewDetailList(Long reviewId){

        var review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new RuntimeException("review"));
        ReviewResponse response = ReviewResponse.fromEntity(review);
        return response;
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
