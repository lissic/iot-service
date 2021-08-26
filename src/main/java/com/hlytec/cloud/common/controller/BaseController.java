package com.hlytec.cloud.common.controller;

import com.hlytec.cloud.common.result.CommonResult;
import com.hlytec.cloud.common.result.ResultEnum;

/**
 * @description: BaseController
 * @author: zero
 * @date: 2021/4/15 17:01
 */
public class BaseController {
    public static CommonResult<Object> success() {
        return new CommonResult<>(ResultEnum.SUCCESS, null);
    }

    public static CommonResult<Object> success(Object data) {
        return new CommonResult<>(ResultEnum.SUCCESS, data);
    }

    public static CommonResult<Object> fail() {
        return new CommonResult<>(ResultEnum.FAIL, null);
    }

    public static CommonResult<Object> fail(ResultEnum resultEnum) {
        return fail(resultEnum.getCode(), resultEnum.getMsg());
    }

    public static CommonResult<Object> fail(String data) {
        return new CommonResult<>(ResultEnum.FAIL, data);
    }

    public static CommonResult<Object> fail(String code, String msg) {
        return new CommonResult<>(code, msg, null);
    }
}
