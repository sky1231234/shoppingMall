package com.project.shop.item.service;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Review;
import com.project.shop.item.dto.request.ItemEditRequest;
import com.project.shop.item.dto.request.ItemRequest;
import com.project.shop.item.dto.request.ReviewEditRequest;
import com.project.shop.item.dto.request.ReviewRequest;
import com.project.shop.item.dto.response.ItemResponse;
import com.project.shop.item.dto.response.ItemReviewResponse;
import com.project.shop.item.dto.response.ReviewResponse;
import com.project.shop.item.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    //상품 - 리뷰 조회
    public ItemReviewResponse itemReviewList(int itemId){
        Item itemID = reviewRepository.itemReview(itemId);
        ItemReviewResponse itemReviewResponse = null;
        return itemReviewResponse;

    }

    //회원 - 리뷰 조회
    public ItemReviewResponse userReviewList(int userId){
        ItemReviewResponse itemReviewResponse = null;
        return itemReviewResponse;

    }

    //리뷰 상세 조회
    public ReviewResponse reviewDetailList(int reviewId){
        //리뷰는 하나지만 안에 리뷰 이미지는 list형태로 나오는가?-아마도?
        Review review = reviewRepository.detailReview(reviewId);
        ReviewResponse response = ReviewResponse.reviewResponse(review);
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
