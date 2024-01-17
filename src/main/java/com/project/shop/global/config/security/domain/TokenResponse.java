package com.project.shop.global.config.security.domain;

import lombok.*;

public record TokenResponse (
        String grantType, String accessToken
) {
}