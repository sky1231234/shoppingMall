package com.project.shop.item.controller;


import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.item.dto.request.ReviewUpdateRequest;
import com.project.shop.item.dto.request.ReviewRequest;
import com.project.shop.item.dto.response.ItemReviewResponse;
import com.project.shop.item.dto.response.ReviewResponse;
import com.project.shop.item.dto.response.UserReviewResponse;
import com.project.shop.item.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api")
@RequiredArgsConstructor
@Validated
public class ReviewController {

    private final ReviewService reviewService;

    //상품 - 리뷰 조회
    @GetMapping("/items/{itemId}/reviews")
    @ResponseStatus(HttpStatus.OK)
    public ItemReviewResponse itemReviewFindAll(@PathVariable("itemId") long itemId){
        return reviewService.itemReviewFindAll(itemId);
    }

    //회원 - 리뷰 조회
    @GetMapping("/users/reviews")
    @ResponseStatus(HttpStatus.OK)
    public UserReviewResponse userReviewFindAll(@AuthenticationPrincipal UserDto userDto){
        return reviewService.userReviewFindAll(userDto);
    }

    //리뷰 상세 조회
    @GetMapping("/reviews/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    public ReviewResponse reviewDetailFind(@PathVariable("reviewId") long reviewId){
        return reviewService.reviewDetailFind(reviewId);
    }

    //리뷰 등록
    @PostMapping("/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public void reviewCreate(@AuthenticationPrincipal UserDto userDto, @RequestBody ReviewRequest reviewRequest){
        reviewService.create(userDto, reviewRequest);
    }

    //리뷰 수정
    @PutMapping("/reviews/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    public void reviewUpdate(@AuthenticationPrincipal UserDto userDto, @PathVariable("reviewId") long reviewId, @RequestBody ReviewUpdateRequest reviewUpdateRequest){
        reviewService.update(userDto, reviewId, reviewUpdateRequest);
    }

    //리뷰 삭제
    @DeleteMapping("/reviews/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    public void reviewDelete(@AuthenticationPrincipal UserDto userDto, @PathVariable("reviewId") long reviewId){
        reviewService.delete(userDto, reviewId);
    }

}
