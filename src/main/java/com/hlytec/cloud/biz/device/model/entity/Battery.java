package com.hlytec.cloud.biz.device.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hlytec.cloud.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

/**
 * @description: Battery
 * @author: zero
 * @date: 2021/5/31 13:46
 */
@Setter
@Getter
@SuperBuilder
@TableName("net_battery")
public class Battery extends BaseEntity<Battery> {

    public Battery() {}

    /**
     * 电池序列号
     */
    @TableField("battery_id")
    private String batteryId;

    /**
     * 电池名称
     */
    @NotEmpty(message = "电池名称不能为空")
    @TableField("name")
    private String name;

    /**
     * 电池组ID
     */
    @NotEmpty(message = "所属电池组不能为空")
    @TableField("battery_group_id")
    private String batteryGrpId;

    @NotEmpty(message = "所属设备不能为空")
    @TableField("device_id")
    private String deviceId;

    /**
     * 单体电压
     */
    @TableField("voltage")
    private Double voltage;

    /**
     * 单体容量
     */
    @TableField("capacity")
    private Double capacity;

    /**
     * 电池类型:0-铅酸蓄电池;1-UPS;2-磷酸铁锂蓄电池;3-超级蓄电池;99-其他
     */
    @TableField("type")
    private Integer type;

    /**
     * 电池状态：0-正常;1-异常
     */
    @TableField("status")
    private Integer status;

}
