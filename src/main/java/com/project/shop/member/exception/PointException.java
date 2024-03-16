package com.project.shop.member.exception;

import lombok.Getter;

@Getter
public enum PointException {

    AVAILABLE_MAXIMUM_POINT("The maximum available point is ")
    ;

    private final String message;

    PointException(String message) {
        this.message = message;
    }
}
