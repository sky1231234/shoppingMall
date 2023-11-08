package com.project.shop.item.dto.request;

import javax.validation.constraints.NotBlank;
import java.util.List;

public record ReviewRequest(
        @NotBlank long userId,
        @NotBlank long itemId,
        @NotBlank long orderId,
        @NotBlank String title,
        @NotBlank String content,
        @NotBlank String star,
        @NotBlank List<ReviewImgEnrollRequest> reviewImgEnrollRequestList
        ) {
}
