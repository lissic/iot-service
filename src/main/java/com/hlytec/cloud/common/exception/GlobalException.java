package com.hlytec.cloud.common.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hlytec.cloud.common.result.CommonResult;
import com.hlytec.cloud.common.result.ResultEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: GlobalExceptionHandler
 * @author: zero
 * @date: 2021/5/25 9:22
 */
@Slf4j
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(NetServiceException.class)
    public CommonResult<Object> bizException(HttpServletRequest request, NetServiceException e) {
        log.error("发生业务异常：" + e.getMessage());
        return new CommonResult<>(e.getCode(),e.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    public CommonResult<Object> otherException(HttpServletRequest request, Exception e) {
        log.error("发生未知异常：" + e.getMessage());
        return new CommonResult<>(ResultEnum.FAIL,e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResult<Object> handle2(MethodArgumentNotValidException ex){
        BindingResult bindingResult = ex.getBindingResult();
        if(bindingResult.hasErrors()){
            FieldError fieldError = bindingResult.getFieldError();
            String defaultMessage = fieldError.getDefaultMessage();
            log.error(ex.getMessage(),ex);
            return new CommonResult<>(ResultEnum.PARAM_INVALIDATE.getCode(), ResultEnum.PARAM_INVALIDATE.getMsg(), defaultMessage);
        }else {
            log.error(ex.getMessage(),ex);
            return new CommonResult<>(ResultEnum.FAIL, ex.getMessage());
        }
    }
}
