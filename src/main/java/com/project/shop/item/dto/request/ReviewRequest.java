package com.project.shop.item.dto.request;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Review;
import com.project.shop.member.domain.Member;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

public record ReviewRequest(
        @NotBlank Item item,
        @NotBlank String title,
        @NotBlank String content,
        @NotBlank int star,
        @NotBlank List<String> reviewImgRequestList
        ) {

        public Review toEntity(Member member){
                return Review.builder()
                        .member(member)
                        .item(this.item())
                        .title(this.title())
                        .content(this.content())
                        .star(this.star())
                        .insertDate(LocalDateTime.now())
                        .updateDate(LocalDateTime.now())
                        .build();
        }
}
