package com.xxf.common.entity;


import com.xxf.common.constant.ErrorCodeEnum;
import lombok.Data;

/**
 * 描述：
 * 返回信息对象
 */
@Data
public class ResultBean<T> extends BaseModel {
    private int resultCode;
    private String resultMessage;
    private T data;

    public ResultBean() {
        resultCode = ErrorCodeEnum.SUCCESS.getCode();
        resultMessage = ErrorCodeEnum.SUCCESS.getMessage();
    }

    public ResultBean(T data) {
        this();
        this.data = data;
    }

    public ResultBean(ErrorCodeEnum error) {
        this.resultCode = error.getCode();
        this.resultMessage = error.getMessage();
    }

    public ResultBean(int resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public ResultBean(int resultCode, String resultMessage, T data) {
        this(resultCode, resultMessage);
        this.data = data;
    }

}
