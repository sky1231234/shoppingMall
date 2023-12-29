package com.project.shop.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "요청하신 페이지를 찾을 수 없습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "" );

    private final HttpStatus resultCode;
    private final String resultMsg;

}
