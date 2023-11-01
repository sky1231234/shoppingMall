package com.project.shop.order.dto;

import javax.validation.constraints.NotBlank;

public record OrderRequest(@NotBlank String loginId, @NotBlank String password) {
}
