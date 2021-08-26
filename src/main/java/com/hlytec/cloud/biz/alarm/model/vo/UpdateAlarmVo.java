package com.hlytec.cloud.biz.alarm.model.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description: UpdateAlarmVo
 * @author: zero
 * @date: 2021/6/25 14:55
 */
@Data
public class UpdateAlarmVo {
    @NotEmpty(message = "告警ID不能为空！")
    private String alarmId;
    @NotNull(message = "告警状态不能为空！")
    private Integer status;
    @NotEmpty(message = "告警结果不能为空！")
    private String handleRes;
}
