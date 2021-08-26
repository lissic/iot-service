package com.hlytec.cloud.biz.alarm.model.vo;

import com.hlytec.cloud.common.entity.BaseQueryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: QueryAlarmVo
 * @author: zero
 * @date: 2021/6/24 9:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryAlarmVo extends BaseQueryEntity {
    /**
     * 0-系统告警;1-设备告警
     */
    private Integer alarmType;
    /**
     * 告警状态：0-未处理；1-已处理
     */
    private Integer status;
    private String deviceId;
    private String deviceName;
    private String alarmStrategyName;
    private String loginName;
    private String area;
}
