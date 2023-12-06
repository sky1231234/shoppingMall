package com.project.shop.item.dto.request;

import javax.validation.constraints.NotBlank;

public record CategoryUpdateRequest(
        @NotBlank String auth,
        @NotBlank String categoryName,
        @NotBlank String brandName
        ) {
}
