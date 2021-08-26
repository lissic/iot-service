package com.hlytec.cloud.biz.alarm.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hlytec.cloud.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * @description: Alarm
 * @author: zero
 * @date: 2021/6/2 9:58
 */
@Setter
@Getter
@SuperBuilder
@TableName("net_alarm")
public class Alarm extends BaseEntity<Alarm> {
    public Alarm() {
    }

    @TableField("alarm_object")
    private String alarmObject;

    @TableField("alarm_strategy_id")
    private String alarmStrategyId;

    @TableField("alarm_val")
    private String alarmVal;

    /**
     * 0-系统告警;1-设备告警
     */
    @TableField("alarm_type")
    private Integer alarmType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("alarm_time")
    private Date alarmTime;

    @TableField("status")
    private Integer status;

    @TableField("device_id")
    private String deviceId;

    @TableField("handle_res")
    private String handleRes;

    @TableField("alarm_item_id")
    private String alarmItemId;

    @TableField(exist = false)
    private String deviceName;

    @TableField(exist = false)
    private String alarmStrategyName;

    @TableField(exist = false)
    private Integer alarmLevel;
}
