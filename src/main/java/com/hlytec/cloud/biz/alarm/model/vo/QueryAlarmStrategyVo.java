package com.hlytec.cloud.biz.alarm.model.vo;

import com.hlytec.cloud.common.entity.BaseQueryEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: QueryAlarmStrategyVo
 * @author: zero
 * @date: 2021/6/17 17:28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryAlarmStrategyVo extends BaseQueryEntity {
    private String name;
    private Integer type;
    private Boolean status;
    private Integer alarmLevel;
    private Integer alarmMethod;
    private String description;
}
