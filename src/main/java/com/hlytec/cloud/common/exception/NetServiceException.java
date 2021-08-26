package com.hlytec.cloud.common.exception;

import com.hlytec.cloud.common.result.ResultEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: NetServiceException 业务通用异常
 * @author: zero
 * @date: 2021/4/16 16:52
 */
@Setter
@Getter
public class NetServiceException extends RuntimeException{
    /**
     * 自定义业务异常码
     */
    protected String code;

    public NetServiceException() {
    }

    public NetServiceException(String message) {
        super(message);
    }

    public NetServiceException(Throwable cause) {
        super(cause);
    }

    public NetServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NetServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    public NetServiceException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
    public NetServiceException(ResultEnum error) {
        super(error.getMsg());
        this.code = error.getCode();
    }
}

