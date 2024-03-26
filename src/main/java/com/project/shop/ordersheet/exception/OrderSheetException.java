package com.project.shop.ordersheet.exception;

import lombok.Getter;

@Getter
public enum OrderSheetException{

    EXCEED_AVAILABLE_POINT("포인트는 상품 금액 이상 사용할 수 없습니다.")
    ;

    private final String message;

    OrderSheetException(String message) {
        this.message = message;
    }


}