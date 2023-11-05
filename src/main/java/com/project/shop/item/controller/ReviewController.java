package com.project.shop.item.controller;


import com.project.shop.item.dto.request.ItemEditRequest;
import com.project.shop.item.dto.request.ItemRequest;
import com.project.shop.item.dto.request.ReviewEditRequest;
import com.project.shop.item.dto.request.ReviewRequest;
import com.project.shop.item.dto.response.ItemResponse;
import com.project.shop.item.dto.response.ItemReviewResponse;
import com.project.shop.item.dto.response.ReviewResponse;
import com.project.shop.item.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/item")
@RequiredArgsConstructor
@Validated
public class ReviewController {

    private final ReviewService reviewService;

    //상품 - 리뷰 조회
    @GetMapping("/review/item")
    @ResponseStatus(HttpStatus.OK)
    public ItemReviewResponse itemReview(@RequestBody int itemId){
        return reviewService.itemReviewList(itemId);
    }

    //회원 - 리뷰 조회
    @GetMapping("/review/user")
    @ResponseStatus(HttpStatus.OK)
    public ItemReviewResponse userReview(@RequestBody int userId){
        return reviewService.userReviewList(userId);
    }

    //리뷰 상세 조회
    @GetMapping("/review/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    public ReviewResponse detailReview(@RequestBody long reviewId){
        return reviewService.reviewDetailList(reviewId);
    }

    //리뷰 등록
    @PostMapping("/review/enroll")
    @ResponseStatus(HttpStatus.CREATED)
    public void reviewEnroll(@RequestBody ReviewRequest reviewRequest){
        reviewService.reviewEnroll(reviewRequest);
    }

    //리뷰 수정
    @PutMapping("/review/{reviewId}}")
    @ResponseStatus(HttpStatus.OK)
    public void reviewEdit(@RequestBody ReviewEditRequest reviewEditRequest){
        reviewService.edit(reviewEditRequest);
    }

    //리뷰 삭제
    @DeleteMapping("/review/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    public void reviewDelete(@RequestBody int reviewId){
        reviewService.delete(reviewId);
    }

}
