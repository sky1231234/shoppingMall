package com.project.shop.ordersheet.exception;

import lombok.Getter;

@Getter
public enum OrderSheetException{

//    AVAILABLE_MAXIMUM_POINT("The maximum available point is ")
    ;

    private final String message;

    OrderSheetException(String message) {
        this.message = message;
    }


}