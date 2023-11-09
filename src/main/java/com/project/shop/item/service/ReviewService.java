package com.project.shop.item.service;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.Review;
import com.project.shop.item.dto.request.ReviewEditRequest;
import com.project.shop.item.dto.request.ReviewRequest;
import com.project.shop.item.dto.response.ItemReviewResponse;
import com.project.shop.item.dto.response.ReviewResponse;
import com.project.shop.item.dto.response.UserReviewResponse;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.ReviewRepository;
import com.project.shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    //상품 - 리뷰 조회
    public ItemReviewResponse itemReviewList(long itemId){
        var list = itemRepository.findById(itemId)
                .orElseThrow(()->new RuntimeException("itemID가 없습니다."));

        return ItemReviewResponse.fromEntity(list);

    }

    //회원 - 리뷰 조회
    //수정
    public UserReviewResponse userReviewList(long userId){
        var list = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("userId 없습니다."));

        return UserReviewResponse.fromEntity(list);

    }

    //리뷰 상세 조회
    public ReviewResponse reviewDetailList(long reviewId){

        var review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new RuntimeException("reviewID가 없습니다."));

        return ReviewResponse.fromEntity(review);
    }

    //리뷰 등록
    public void reviewEnroll(ReviewRequest reviewRequest){
        reviewRepository.save(reviewRequest.toEntity());
    }

    //리뷰 수정
    public void edit(ReviewEditRequest reviewEditRequest){
        reviewRepository.save(reviewEditRequest.toEntity());
    }

    //리뷰 삭제
    public void delete(long reviewId){
        reviewRepository.deleteById(reviewId);
    }
}
