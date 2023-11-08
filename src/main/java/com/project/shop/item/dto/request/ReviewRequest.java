package com.project.shop.item.dto.request;

import com.project.shop.item.domain.Review;
import com.project.shop.item.dto.response.ItemReviewResponse;
import com.project.shop.item.dto.response.UserReviewResponse;
import com.project.shop.user.domain.User;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

public record ReviewRequest(
        @NotBlank long userId,
        @NotBlank long itemId,
        @NotBlank long orderId,
        @NotBlank String title,
        @NotBlank String content,
        @NotBlank String star,
        @NotBlank List<ReviewImgEnrollRequest> reviewImgEnrollRequestList
        ) {

        public ReviewRequest toEntity(Review review){
                return ReviewRequest.builder()
                        .userId(userId)
                        .itemId(itemId)
                        .orderId()
                        .title()
                        .content()
                        .star()
                        .reviewImgEnrollRequestList()
                        .build();
        }
}
