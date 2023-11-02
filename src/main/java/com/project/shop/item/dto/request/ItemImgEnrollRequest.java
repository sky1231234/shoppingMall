package com.project.shop.item.dto.request;

import javax.validation.constraints.NotBlank;

public record ItemImgEnrollRequest(
        @NotBlank String imgUrl

        ) {
}
