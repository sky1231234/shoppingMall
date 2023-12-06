package com.project.shop.user.dto.request;

import javax.validation.constraints.NotBlank;

public record CartUpdateRequest(
        @NotBlank int count,
        @NotBlank long optionNum
        ) {
}
