package com.project.shop.item.dto.request;

import com.project.shop.item.domain.Review;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public record ReviewRequest(
        @NotBlank long userId,
        @NotBlank long itemId,
        @NotBlank String title,
        @NotBlank String content,
        @NotBlank String star,
        @NotBlank List<ReviewImgRequest> reviewImgRequestList
        ) {

        public Review toEntity(ReviewRequest reviewRequest){

                var imgList = reviewRequest.getReviewImgRequestList()
                        .stream().map(reviewImgRequest -> reviewImgRequest.toEntity(reviewImgRequest))
                        .collect(Collectors.toList());

                return Review.builder()
                        //수정 userid를 전달하면 user로 어떻게 전달할
                        .user()
                        .itemId(reviewRequest.getItemId())
                        .title(reviewRequest.getTitle())
                        .content(reviewRequest.getContent())
                        .star(reviewRequest.getStar())
                        .reviewImgEnrollRequestList(imgList)
                        .build();
        }
}
