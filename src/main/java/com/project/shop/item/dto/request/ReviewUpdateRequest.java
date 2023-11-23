package com.project.shop.item.dto.request;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Review;
import com.project.shop.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.List;


@Builder
@Getter
public record ReviewUpdateRequest(
        @NotBlank User user,
        @NotBlank Item item,
        @NotBlank String title,
        @NotBlank String content,
        @NotBlank int star,
        @NotBlank List<ReviewImgUpdateRequest> reviewImgUpdateRequest){

    public Review toEntity(){
        return Review.builder()
                .user(this.getUser())
                .item(this.getItem())
                .title(this.getTitle())
                .content(this.getContent())
                .star(this.getStar())
                .build();
    }
}
