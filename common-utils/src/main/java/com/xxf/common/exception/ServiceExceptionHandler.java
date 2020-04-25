package com.xxf.common.exception;

import com.alibaba.fastjson.JSONException;

import com.xxf.common.constant.ErrorCodeEnum;
import com.xxf.common.entity.ResultBean;
import com.fasterxml.jackson.core.JsonParseException;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;



/**
 * @Description 异常控制
 * @Date Created in 2020/3/19 11:26
 * @User xxf
 */
@Slf4j
@RestControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResultBean processException(HttpServletRequest request, ServiceException ex, HandlerMethod handler) {
        log.warn(request.getMethod() + "\t"
                + request.getRequestURI() + "\t"
                + handler.getBeanType().toString() + "\t"
                + handler.getMethod().getName() + "\t"
                + ex.toString());
        log.warn(ex.getStackTrace()[0].toString());

        return new ResultBean(ex.getCode(), ex.getMessage());
    }

    /**
     * @PathVariable 获取参数时捕获异常
     * @param request
     * @param ex
     * @param handler
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResultBean processMethodArgumentTypeMismatchException(HttpServletRequest request, MethodArgumentTypeMismatchException ex, HandlerMethod handler) {
        log.error(request.getMethod() + "\t"
                + request.getRequestURI() + "\t"
                + handler.getMethod().getName() + "\t"
                + handler.getBeanType().toString() + "\t"
                + ex.toString());
        log.error(ex.getMessage(), ex);
        Throwable throwable = Throwables.getRootCause(ex);

        if (throwable instanceof NumberFormatException) {
            return new ResultBean<>(ErrorCodeEnum.PARAM_VALID_ERROR);
        }
        return new ResultBean<>(ErrorCodeEnum.ERROR.getCode(), ErrorCodeEnum.ERROR.getMessage());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResultBean processHttpMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException ex, HandlerMethod handler) {
        log.error(request.getMethod() + "\t"
                + request.getRequestURI() + "\t"
                + handler.getMethod().getName() + "\t"
                + handler.getBeanType().toString() + "\t"
                + ex.toString());
        log.error(ex.getMessage(), ex);

        Throwable throwable = Throwables.getRootCause(ex);
        //增加json转换异常报错提示
        if (throwable instanceof JsonParseException) {
            return new ResultBean<>(ErrorCodeEnum.JSON_TRANSFORM_ERROR);
        }
        if (throwable instanceof NumberFormatException) {
            return new ResultBean<>(ErrorCodeEnum.JSON_TRANSFORM_ERROR);
        }
        return new ResultBean<>(ErrorCodeEnum.ERROR.getCode(), ErrorCodeEnum.ERROR.getMessage());
    }

    /**
     * 参数校验异常MethodArgumentNotValidException
     *
     * @param request
     * @param exception
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultBean validException(HttpServletRequest request,
                                     MethodArgumentNotValidException exception) {
        //按需重新封装需要返回的错误信息
        //解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
        StringBuffer errorInfo = new StringBuffer();
        StringBuffer info = new StringBuffer();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errorInfo.append(request.getMethod()).append("\t").append(request.getRequestURI()).append("\t").append(error.getDefaultMessage()).append("\t").append(error.getField());
            info.append(error.getField()).append(error.getDefaultMessage());
        }
        log.error(errorInfo.toString());
        return new ResultBean<>(ErrorCodeEnum.PARAM_VALID_ERROR.getCode(), info.toString());
    }

    /**
     * 参数校验异常BindException  get请求主要
     *
     * @param request
     * @param exception
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    public ResultBean validBindException(HttpServletRequest request,
                                         BindException exception) {
        //按需重新封装需要返回的错误信息
        //解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
        StringBuffer errorInfo = new StringBuffer();
        StringBuffer info = new StringBuffer();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errorInfo.append(request.getMethod()).append("\t").append(request.getRequestURI()).append("\t").append(error.getDefaultMessage()).append("\t").append(error.getField());
            info.append(error.getDefaultMessage()).append(" ").append(error.getField());
        }
        log.error(errorInfo.toString());
        return new ResultBean<>(ErrorCodeEnum.PARAM_VALID_ERROR.getCode(), info.toString());
    }


    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResultBean httpRequestTypeException(HttpServletRequest request,
                                        Exception ex) {

        log.error(ex.getMessage(), ex);
        return new ResultBean(ErrorCodeEnum.HTTP_TYPE_NOT_SUPPORT);
    }


    @ExceptionHandler(value = JSONException.class)
    public ResultBean jsonErrTypeException(HttpServletRequest request,
                                               Exception ex) {

        log.error(ex.getMessage(), ex);
        return new ResultBean(ErrorCodeEnum.JSON_TRANSFORM_ERROR);
    }


    @ExceptionHandler(value = Exception.class)
    public ResultBean validAllException(HttpServletRequest request,
                                        Exception ex) {


        log.error(ex.getMessage(), ex);
        return new ResultBean(ErrorCodeEnum.ERROR);
    }
}
