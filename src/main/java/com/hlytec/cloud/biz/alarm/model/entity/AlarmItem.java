package com.hlytec.cloud.biz.alarm.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hlytec.cloud.common.entity.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description: AlarmItem
 * @author: zero
 * @date: 2021/6/2 9:47
 */
@Setter
@Getter
@SuperBuilder
@TableName("net_alarm_item")
public class AlarmItem extends BaseEntity<AlarmItem> {
    public AlarmItem() {
    }

    @NotEmpty(message = "告警编码不能为空")
    @TableField("item_code")
    private String itemCode;

    @NotEmpty(message = "告警项名称不能为空")
    @TableField("item_name")
    private String itemName;

    @NotNull(message = "标准值不能为空")
    @TableField("standard_val")
    private Integer standardVal;

    @NotNull(message = "阈值不能为空")
    @TableField("threshold_val")
    private Integer thresholdVal;

}
