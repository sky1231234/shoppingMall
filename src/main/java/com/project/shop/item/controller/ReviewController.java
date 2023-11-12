package com.project.shop.item.controller;


import com.project.shop.item.dto.request.ReviewUpdateRequest;
import com.project.shop.item.dto.request.ReviewRequest;
import com.project.shop.item.dto.response.ItemReviewResponse;
import com.project.shop.item.dto.response.ReviewResponse;
import com.project.shop.item.dto.response.UserReviewResponse;
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
    public ItemReviewResponse itemReviewFindAll(@RequestBody int itemId){
        return reviewService.itemReviewFindAll(itemId);
    }

    //회원 - 리뷰 조회
    @GetMapping("/review/user")
    @ResponseStatus(HttpStatus.OK)
    public UserReviewResponse userReviewFindAll(@RequestBody int userId){
        return reviewService.userReviewFindAll(userId);
    }

    //리뷰 상세 조회
    @GetMapping("/review/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    public ReviewResponse reviewDetailFind(@RequestBody long reviewId){
        return reviewService.reviewDetailFind(reviewId);
    }

    //리뷰 등록
    @PostMapping("/review")
    @ResponseStatus(HttpStatus.CREATED)
    public void reviewCreate(@RequestBody ReviewRequest reviewRequest){
        reviewService.create(reviewRequest);
    }

    //리뷰 수정
    @PutMapping("/review/{reviewId}}")
    @ResponseStatus(HttpStatus.OK)
    public void reviewUpdate(@RequestBody ReviewUpdateRequest reviewUpdateRequest){
        reviewService.update(reviewUpdateRequest);
    }

    //리뷰 삭제
    @DeleteMapping("/review/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    public void reviewDelete(@RequestBody int reviewId){
        reviewService.delete(reviewId);
    }

}
