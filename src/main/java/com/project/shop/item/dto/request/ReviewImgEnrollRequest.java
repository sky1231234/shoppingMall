package com.project.shop.item.dto.request;

import javax.validation.constraints.NotBlank;

public record ReviewImgEnrollRequest(
        @NotBlank String imgUrl

        ) {
}
