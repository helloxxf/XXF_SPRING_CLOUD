package com.xxf.common.entity;

import com.xxf.common.constant.ErrorCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @Description 公用返回对象
 * @Date Created in 2020/3/19 11:26
 * @User xxf
 */
@Data
@AllArgsConstructor
@ToString
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

}
