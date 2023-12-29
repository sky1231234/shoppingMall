package com.project.shop.global.exception;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import org.springframework.http.HttpStatus;
//
//@Getter
//@AllArgsConstructor
//public enum ErrorCode {
//    TEST(HttpStatus.INTERNAL_SERVER_ERROR,"테스트 에러");
////    NOT_FOUND_ITEM(HttpStatus.NOT_FOUND, "상품이 존재하지 않습니다."),
////    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND, "리뷰가 존재하지 않습니다."),
////    ;
//
//    private HttpStatus httpStatus;
//    private String msg;
//
//}

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String name();
    HttpStatus getResultCode();
    String getResultMsg();
}
