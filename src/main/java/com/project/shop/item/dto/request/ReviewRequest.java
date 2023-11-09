package com.project.shop.item.dto.request;

import com.project.shop.item.domain.Review;
import com.project.shop.item.dto.response.ItemReviewResponse;
import com.project.shop.item.dto.response.UserReviewResponse;
import com.project.shop.user.domain.User;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record ReviewRequest(
        @NotBlank long userId,
        @NotBlank long itemId,
        @NotBlank String title,
        @NotBlank String content,
        @NotBlank String star,
        @NotBlank List<ReviewImgEnrollRequest> reviewImgEnrollRequestList
        ) {

        public Review toEntity(){
                return Review.builder()
                        .userId(userId)
                        .itemId(itemId)
                        .title(title)
                        .content(content)
                        .star(star)
                        .reviewImgEnrollRequestList(reviewImgEnrollRequestList)
                        .build();
        }
}
