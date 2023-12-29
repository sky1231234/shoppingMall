package com.project.shop.global.exception;

import lombok.Data;

@Data
public class HeaderVO {
    private int status;
    private String message;
    private Integer size;
    private String error;
}
