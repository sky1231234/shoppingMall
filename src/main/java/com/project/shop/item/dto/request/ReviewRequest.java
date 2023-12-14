package com.project.shop.item.dto.request;

import com.project.shop.item.domain.Item;
import com.project.shop.item.domain.Review;
import com.project.shop.user.domain.User;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ReviewRequest(
        @NotBlank User user,
        @NotBlank Item item,
        @NotBlank String title,
        @NotBlank String content,
        @NotBlank int star,
        @NotBlank List<String> reviewImgRequestList
        ) {

        public Review toEntity(){
                LocalDateTime now = LocalDateTime.now();

                return Review.builder()
                        .users(this.user())
                        .item(this.item())
                        .title(this.title())
                        .content(this.content())
                        .star(this.star())
                        .insertDate(now)
                        .updateDate(now)
                        .build();
        }
}
