package com.project.shop.item.controller;


import com.project.shop.item.dto.request.ReviewUpdateRequest;
import com.project.shop.item.service.ReviewService;
import com.project.shop.global.config.security.domain.UserDto;
import com.project.shop.item.dto.request.ReviewRequest;
import com.project.shop.item.dto.response.ItemReviewResponse;
import com.project.shop.item.dto.response.ReviewResponse;
import com.project.shop.item.dto.response.UserReviewResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Validated
@Tag( name = "ReviewController", description = "[사용자] 리뷰 API")
public class ReviewController {

    private final ReviewService reviewService;

    @PreAuthorize("hasRole('ADMIN','USER')")
    @GetMapping("/items/{itemId}")
    public ResponseEntity<ItemReviewResponse> findAllByItem(@PathVariable("itemId") long itemId){
        return ResponseEntity.ok()
                .body(reviewService.findAllByItem(itemId));
    }

    @PreAuthorize("hasRole('ADMIN','USER')")
    @GetMapping("/members")
    public ResponseEntity<UserReviewResponse> findAllByMember(@AuthenticationPrincipal UserDto userDto){
        return ResponseEntity.ok()
                .body(reviewService.findAllByMember(userDto.getLoginId()));
    }

    @PreAuthorize("hasRole('ADMIN','USER')")
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> detailFind(@PathVariable("reviewId") long reviewId){
        return ResponseEntity.ok()
                .body(reviewService.detailFind(reviewId));
    }

    @PreAuthorize("hasRole('ADMIN','USER')")
    @PostMapping
    public ResponseEntity<HttpStatus> create(@AuthenticationPrincipal UserDto userDto, @RequestBody ReviewRequest reviewRequest){
        long reviewId = reviewService.create(userDto.getLoginId(), reviewRequest);
        return ResponseEntity.created(URI.create("/reviews"+reviewId)).build();
    }

    @PreAuthorize("hasRole('ADMIN','USER')")
    @PutMapping("/{reviewId}")
    public ResponseEntity<HttpStatus> update(@AuthenticationPrincipal UserDto userDto, @PathVariable("reviewId") long reviewId, @RequestBody ReviewUpdateRequest reviewUpdateRequest){
        reviewService.update(userDto.getLoginId(), reviewId, reviewUpdateRequest);
        return ResponseEntity.ok().build();
    }

    //리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<HttpStatus> delete(@AuthenticationPrincipal UserDto userDto, @PathVariable("reviewId") long reviewId){
        reviewService.delete(userDto.getLoginId(), reviewId);
        return ResponseEntity.noContent().build();

    }

}
