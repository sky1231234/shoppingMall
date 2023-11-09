package com.project.shop.item.dto.request;

import com.project.shop.item.domain.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Builder
public record ReviewEditRequest(
        @NotBlank long userId,
        @NotBlank long reviewId,
        @NotBlank String title,
        @NotBlank String content,
        @NotBlank String star,
        @NotBlank List<ReviewImgEnrollRequest> reviewImgEnrollRequestList){

    public Review toEntity(){
        return Review.builder()
                .userId(userId)
                .itemId(reviewId)
                .title(title)
                .content(content)
                .star(star)
                .reviewImgEnrollRequestList(reviewImgEnrollRequestList)
                .build();
    }
}
