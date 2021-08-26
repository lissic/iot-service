package com.hlytec.cloud.biz.alarm.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hlytec.cloud.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description: AlarmStrategy
 * @author: zero
 * @date: 2021/6/2 9:09
 */
@Setter
@Getter
@SuperBuilder
@TableName("net_alarm_strategy")
public class AlarmStrategy extends BaseEntity<AlarmStrategy> {
    public AlarmStrategy() {
    }

    @NotEmpty(message = "策略名称不能为空")
    @TableField("name")
    private String name;

    /**
     * 策略类型：0-主动上报;1-被动监控
     */
    @NotNull(message = "告警类型不能为空")
    @TableField("type")
    private Integer type;

    /**
     * 是否启用：0-否;1-是
     */
    @TableField("status")
    private Boolean status = Boolean.TRUE;

    /**
     * 告警级别：0-非告警;1-一般告警;2-中度告警;3-严重告警
     */
    @NotNull(message = "告警级别不能为空")
    @TableField("alarm_level")
    private Integer alarmLevel;

    /**
     * 告警方式：0-不告警;1-弹窗告警;2-声音告警;3-APP告警;4-短信告警;5-邮件告警;6-电话告警;99-其他
     */
    @NotEmpty(message = "告警方式不能为空")
    @TableField("alarm_method")
    private String alarmMethod;

    @TableField("description")
    private String description;

    @TableField("alarm_items")
    private String alarmItems;

    /**
     * 告警规则：0-重复次数;1-时间间隔
     */
    @NotNull(message = "告警规则不能为空")
    @TableField("alarm_rule")
    private Integer alarmRule;

    /**
     * 告警时间间隔：单位分钟
     */
    @TableField("alarm_repeat_time")
    private Integer alarmRepeatTime;

    @TableField("alarm_repeat_num")
    private Integer alarmRepeatNum;

    @TableField(exist = false)
    private List<String> itemNameList;

}
