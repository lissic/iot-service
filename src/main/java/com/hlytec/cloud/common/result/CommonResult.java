package com.hlytec.cloud.common.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Description: CommonResult
 * @Author: zero
 * Date: 2021/4/15 11:41
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class CommonResult<T> {

    @JsonProperty(index = 1)
    private String code;
    @JsonProperty(index = 2)
    private String msg;
    @JsonProperty(index = 3)
    private T data;

    public CommonResult(ResultEnum codeEnum, T data) {
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMsg();
        this.data = data;
    }

}
