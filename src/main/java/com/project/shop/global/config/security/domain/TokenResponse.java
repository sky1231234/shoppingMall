package com.project.shop.global.config.security.domain;

public record TokenResponse (
        String grantType, String accessToken
) {
}