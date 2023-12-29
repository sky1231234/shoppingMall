package com.project.shop.member.dto.request;

import javax.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank String loginId,
        @NotBlank String password
    ) {
    }
