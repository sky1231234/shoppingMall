package com.project.shop.item.exception;

import com.project.shop.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ItemException extends RuntimeException {

    private ErrorCode errorCode;


    public ItemException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}