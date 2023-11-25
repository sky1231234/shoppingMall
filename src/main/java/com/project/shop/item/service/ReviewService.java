package com.project.shop.item.service;

import com.project.shop.item.domain.*;
import com.project.shop.item.dto.request.*;
import com.project.shop.item.dto.response.ItemReviewResponse;
import com.project.shop.item.dto.response.ReviewResponse;
import com.project.shop.item.dto.response.UserInReviewResponse;
import com.project.shop.item.dto.response.UserReviewResponse;
import com.project.shop.item.exception.ItemException;
import com.project.shop.item.repository.ItemImgRepository;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.ReviewImgRepository;
import com.project.shop.item.repository.ReviewRepository;
import com.project.shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.project.shop.global.exception.ErrorCode.NOT_FOUND_REVIEW;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImgRepository reviewImgRepository;

    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final UserRepository userRepository;

    //상품 - 리뷰 조회
    public ItemReviewResponse itemReviewFindAll(long itemId){
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("NOT_FOUNT_ITEM"));

        List<Review> reviewList = reviewRepository.findAllByItemId(item);

        //수정 썸네일 형식 sql문 돌리기
        ItemImg thumbnail = itemImgRepository.findByItemIdAndMainImg(itemId,"Y");

        return ItemReviewResponse.fromEntity(item, thumbnail, reviewList);

    }

    //회원 - 리뷰 조회
    public UserReviewResponse userReviewFindAll(long userId){

        //수정하기
        List<Review> userList = reviewRepository.findAllByUserId(userId);

        List<Item> itemData = new ArrayList<>();

        //리뷰 하나 + 상품 정보 하나
        for (Review review : userList) {
            var item = review.getItem();

        }

        return UserReviewResponse.fromEntity(userList,itemData);

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

        //review
        Review review = reviewRepository.findById(reviewId)
                        .orElseThrow(() -> new RuntimeException("NOT_FOUND_REVIEW"));

        review.editReview(reviewUpdateRequest);

        //reviewImg
        List<ReviewImg> reviewImgList = reviewImgRepository.findByReviewId(reviewId);

        if(reviewImgList.isEmpty()){
            throw new RuntimeException("NOT_FOUND_REVIEW_IMG");
        }
        reviewImgRepository.deleteAll(reviewImgList);

        List<ReviewImg> reviewImgUpdateList = reviewUpdateRequest
                .getReviewImgUpdateRequest()
                .stream()
                .map(ReviewImgUpdateRequest::toEntity)
                .collect(Collectors.toList());

        for (ReviewImg reviewImg : reviewImgUpdateList) {
            reviewImg.updateReview(review);
        }

        reviewImgRepository.saveAll(reviewImgUpdateList);
    }

    //리뷰 삭제
    public void delete(long reviewId){
        reviewRepository.deleteById(reviewId);
    }
}
