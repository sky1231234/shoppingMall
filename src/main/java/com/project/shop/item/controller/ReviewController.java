package com.project.shop.item.controller;


import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.item.dto.request.ReviewUpdateRequest;
import com.project.shop.item.dto.request.ReviewRequest;
import com.project.shop.item.dto.response.ItemReviewResponse;
import com.project.shop.item.dto.response.ReviewResponse;
import com.project.shop.item.dto.response.UserReviewResponse;
import com.project.shop.item.service.ReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Validated
@Tag( name = "ReviewController", description = "[사용자] 리뷰 API")
public class ReviewController {

    private final ReviewService reviewService;

    //상품 - 리뷰 조회
    @GetMapping("/items/{itemId}/reviews")
    public ResponseEntity<ItemReviewResponse> findAllByItem(@PathVariable("itemId") long itemId){
        return ResponseEntity.ok()
                .body(reviewService.findAllByItem(itemId));
    }

    //회원 - 리뷰 조회
    @GetMapping("/members/reviews")
    public ResponseEntity<UserReviewResponse> findAllByMember(@AuthenticationPrincipal UserDto userDto){
        return ResponseEntity.ok()
                .body(reviewService.findAllByMember(userDto.getLoginId()));
    }

    //리뷰 상세 조회
    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponse> detailFind(@PathVariable("reviewId") long reviewId){
        return ResponseEntity.ok()
                .body(reviewService.detailFind(reviewId));
    }

    //리뷰 등록
    @PostMapping("/reviews")
    public ResponseEntity<HttpStatus> create(@AuthenticationPrincipal UserDto userDto, @RequestBody ReviewRequest reviewRequest){
        long reviewId = reviewService.create(userDto.getLoginId(), reviewRequest);
        return ResponseEntity.created(URI.create("/reviews"+reviewId)).build();
    }

    //리뷰 수정
    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<HttpStatus> update(@AuthenticationPrincipal UserDto userDto, @PathVariable("reviewId") long reviewId, @RequestBody ReviewUpdateRequest reviewUpdateRequest){
        reviewService.update(userDto.getLoginId(), reviewId, reviewUpdateRequest);
        return ResponseEntity.ok().build();
    }

    //리뷰 삭제
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<HttpStatus> delete(@AuthenticationPrincipal UserDto userDto, @PathVariable("reviewId") long reviewId){
        reviewService.delete(userDto.getLoginId(), reviewId);
        return ResponseEntity.noContent().build();

    }

}
