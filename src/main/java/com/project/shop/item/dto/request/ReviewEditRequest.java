package com.project.shop.item.dto.request;

import javax.validation.constraints.NotBlank;
import java.util.List;

public record ReviewEditRequest(
        @NotBlank long userId,
        @NotBlank long reviewId,
        @NotBlank String title,
        @NotBlank String content,
        @NotBlank String star,
        @NotBlank List<ReviewImgEnrollRequest> reviewImgEnrollRequestList){

}
