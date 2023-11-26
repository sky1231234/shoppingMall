package com.project.shop.item.service;

import com.project.shop.item.domain.*;
import com.project.shop.item.dto.request.*;
import com.project.shop.item.dto.response.*;
import com.project.shop.item.exception.ItemException;
import com.project.shop.item.repository.ItemImgRepository;
import com.project.shop.item.repository.ItemRepository;
import com.project.shop.item.repository.ReviewImgRepository;
import com.project.shop.item.repository.ReviewRepository;
import com.project.shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImgRepository reviewImgRepository;

    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;

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

        List<Review> reviewList = reviewRepository.findAllByUserId(userId);

        var list = reviewList.stream().map(x -> {
            return UserReviewResponse.ReviewItem.builder()
                    .itemId(x.getItem().getItemId())
                    .categoryName(x.getItem().getCategory().getCategoryName())
                    .brandName(x.getItem().getCategory().getBrandName())
                    .itemThumbnail(x.getItem().getItemImgList().stream()
                            .filter(y -> y.getItemImgType() == ItemImgType.Y)
                            .map(y -> {
                                return UserReviewResponse.ReviewItem.Thumbnail.builder()
                                        .imgId(y.getItemImgId())
                                        .url(y.getImgUrl())
                                        .build();
                            }).findFirst().orElse(null)
                        )
                    .reviewTitle(x.getTitle())
                    .reviewContent(x.getContent())
                    .reviewStar(x.getStar())
                    .insertDate(x.getInsertDate())
                    .build();
        }).toList();

        return UserReviewResponse.builder()
                        .userId(userId)
                        .reviewItemList(list)
                        .build();
    }

    //리뷰 상세 조회
    public ReviewResponse reviewDetailFind(long reviewId){

        var review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new RuntimeException("NOT_FOUND_REVIEW"));

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
