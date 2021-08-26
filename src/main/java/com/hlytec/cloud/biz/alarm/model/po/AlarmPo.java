package com.hlytec.cloud.biz.alarm.model.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @description: AlarmPo
 * @author: zero
 * @date: 2021/7/2 14:52
 */
@Data
public class AlarmPo {
    private String alarmObject;

    private String alarmStrategyId;

    private String alarmVal;

    /**
     * 0-系统告警;1-设备告警
     */
    private Integer alarmType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date alarmTime;

    private Integer status;

    private String deviceId;

    private String handleRes;

    private String deviceName;

    private String alarmStrategyName;

    private Integer alarmLevel;

    private Integer standardVal;

    private Integer thresholdVal;
}
