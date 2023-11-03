package com.project.shop.item.service;

import com.project.shop.item.domain.Review;
import com.project.shop.item.dto.request.ItemEditRequest;
import com.project.shop.item.dto.request.ItemRequest;
import com.project.shop.item.dto.request.ReviewEditRequest;
import com.project.shop.item.dto.request.ReviewRequest;
import com.project.shop.item.dto.response.ItemResponse;
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
    //ItemResponse 전체가 나와야함
    public ItemResponse itemReviewList(int itemId){
        ItemResponse itemResponse = null;
        return itemResponse;

    }

    //회원 - 리뷰 조회
    //ItemResponse 전체가 나와야함
    public ItemResponse userReviewList(int userId){
        ItemResponse itemResponse = null;
        return itemResponse;

    }

    //리뷰 상세 조회
    public ReviewResponse reviewDetailList(int reviewId){
        List<Review> review = reviewRepository.detailReview(reviewId);

//        return new ReviewResponse(review);
            return review.map(ReviewResponse::new);
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
