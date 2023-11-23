package com.project.shop.item.service;

import com.project.shop.item.domain.ItemImg;
import com.project.shop.item.domain.Review;
import com.project.shop.item.domain.ReviewImg;
import com.project.shop.item.dto.request.ItemImgRequest;
import com.project.shop.item.dto.request.ReviewImgRequest;
import com.project.shop.item.dto.request.ReviewUpdateRequest;
import com.project.shop.item.dto.request.ReviewRequest;
import com.project.shop.item.dto.response.ItemReviewResponse;
import com.project.shop.item.dto.response.ReviewResponse;
import com.project.shop.item.dto.response.UserReviewResponse;
import com.project.shop.item.exception.ItemException;
import com.project.shop.item.repository.ItemImgRepository;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.ReviewImgRepository;
import com.project.shop.item.repository.ReviewRepository;
import com.project.shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.shop.global.exception.ErrorCode.NOT_FOUND_REVIEW;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImgRepository reviewImgRepository;

    private final ItemImgRepository itemImgRepository;
    private final UserRepository userRepository;

    //상품 - 리뷰 조회
    public ItemReviewResponse itemReviewFindAll(long itemId){
        //수정 썸네일 형식
        ItemImg thumbnail = itemImgRepository.findByItemIdAndMainImg(itemId,"Y");
        List<Review> list = reviewRepository.findByItemId(itemId);

        return ItemReviewResponse.fromEntity(thumbnail, list);

    }

    //회원 - 리뷰 조회
    public UserReviewResponse userReviewFindAll(long userId){
        //수정
        List<Review> list = reviewRepository.findByUserId(userId);

        return UserReviewResponse.fromEntity(list);

    }

    //리뷰 상세 조회
    public ReviewResponse reviewDetailFind(long reviewId){

        var review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new ItemException(NOT_FOUND_REVIEW));

        //상품 메인 이미지 불러오기
//        itemImgRepository.findByItemIdAndMainImg();

        return ReviewResponse.fromEntity(review);
    }

    //리뷰 등록
    public void create(ReviewRequest reviewRequest){
        var review = reviewRequest.toEntity();
        reviewRepository.save(review);

        //reviewImg
        List<ReviewImg> reviewImgList = reviewRequest
                .getReviewImgRequestList()
                .stream()
                .map(ReviewImgRequest::toEntity)
                .collect(Collectors.toList());

        for (ReviewImg reviewImg : reviewImgList) {
            reviewImg.updateReview(review);
        }

        reviewImgRepository.saveAll(reviewImgList);
    }

    //리뷰 수정
    public void update(long reviewId, ReviewUpdateRequest reviewUpdateRequest){

        Review review = reviewRepository.findById(reviewId)
                        .orElseThrow(() -> new RuntimeException("NOT_FOUND_REVIEW"));

        review.editReview(reviewUpdateRequest);

    }

    //리뷰 삭제
    public void delete(long reviewId){
        reviewRepository.deleteById(reviewId);
    }
}
