package com.project.shop.user.dto.request;

import javax.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank String loginId, @NotBlank String password) {
}
