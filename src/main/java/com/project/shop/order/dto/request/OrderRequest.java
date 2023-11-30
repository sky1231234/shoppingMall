package com.project.shop.order.dto.request;

import javax.validation.constraints.NotBlank;

public record OrderRequest(@NotBlank String loginId, @NotBlank String password) {
}
