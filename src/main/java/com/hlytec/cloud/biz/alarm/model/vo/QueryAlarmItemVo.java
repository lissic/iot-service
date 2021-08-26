package com.hlytec.cloud.biz.alarm.model.vo;

import com.hlytec.cloud.common.entity.BaseQueryEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: QueryAlarmItemVo
 * @author: zero
 * @date: 2021/6/17 10:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryAlarmItemVo extends BaseQueryEntity {

    private String itemCode;

    private String itemName;

    private Integer standardVal;

    private Integer thresholdVal;

}
