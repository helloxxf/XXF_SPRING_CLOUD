package com.xxf.springcloud.constant;

public enum Result {

    SUCCESS(200, "success"),
    ERROR(500, "error"),
    HTTP_TYPE_NOT_SUPPORT(10002, "http request type not support"),
    PARAM_VALID_ERROR(10003, "数据格式错误");

    private int code;

    private String resultMesage;

    Result(int code, String resultMesage) {
        this.code = code;
        this.resultMesage = resultMesage;
    }

    public int getCode() {
        return code;
    }

    public String getResultMesage() {
        return resultMesage;
    }
}
