package com.xxf.common.exception;

import com.alibaba.fastjson.JSONObject;

import com.xxf.common.constant.ErrorCodeEnum;
import lombok.Data;

/**
 * @Description 也无异常操作
 * @Date Created in 2020/3/19 11:26
 * @User xxf
 */
@Data
public class ServiceException extends RuntimeException {
    private int code;
    private String message;

    public ServiceException() {
        this(ErrorCodeEnum.ERROR.getCode(), ErrorCodeEnum.ERROR.getMessage());
    }

    public ServiceException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ServiceException(ErrorCodeEnum errorCodeEnum) {
        this(errorCodeEnum.getCode(), errorCodeEnum.getMessage());
    }

    public ServiceException(String message) {
        this(ErrorCodeEnum.ERROR.getCode(), message);
    }

    @Override
    public String toString() {
        JSONObject result = new JSONObject();
        result.put("resultCode", code);
        result.put("resultMessage", message);
        return result.toString();
    }
}
