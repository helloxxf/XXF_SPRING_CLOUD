package com.xxf.common.constant;

/**
 * 描述：错误码枚举
 * 返回错误编码
 */
public enum ErrorCodeEnum {

    //公用返回码
    SUCCESS(200, "success"),
    ERROR(10001, "unknown error"),
    HTTP_TYPE_NOT_SUPPORT(10002, "http request type not support");

    private int code;
    private String message;

    ErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}