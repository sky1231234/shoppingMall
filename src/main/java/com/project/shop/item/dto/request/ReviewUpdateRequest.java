package com.project.shop.item.dto.request;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Review;
import com.project.shop.member.domain.Member;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Builder
public record ReviewUpdateRequest(
        @NotBlank Member member,
        @NotBlank Item item,
        @NotBlank String title,
        @NotBlank String content,
        @NotBlank int star,
        @NotBlank List<String> reviewImgUpdateRequest){

    public Review toEntity(){
        return Review.builder()
                .users(this.member())
                .item(this.item())
                .title(this.title())
                .content(this.content())
                .star(this.star())
                .build();
    }
}
