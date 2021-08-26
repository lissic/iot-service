package com.hlytec.cloud.biz.device.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hlytec.cloud.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @description: DeviceMonitor
 * @author: zero
 * @date: 2021/5/31 13:40
 */
@Setter
@Getter
@SuperBuilder
@TableName("net_device_monitor")
public class DeviceMonitor extends BaseEntity<DeviceMonitor> {

    public DeviceMonitor() {}

    @TableField("device_id")
    private String deviceId;

    @TableField("rectifier_vol")
    private Double rectifierVol;

    @TableField("env_temp")
    private Double envTemp;

    @TableField("battery_group_id")
    private String batteryGrpId;

    @TableField("alarm_num")
    private Integer alarmNum;

    @TableField("output_electric")
    private Double outputElectric;

    @TableField("electric_quantity")
    private Double electricQuantity;

    @TableField("suggested_load")
    private Double suggestedLoad;

    @TableField("real_load")
    private Double realLoad;

    /**
     * 环境湿度
     */
    @TableField("env_humidity")
    private Double envHumidity;

    /**
     * 电池总电压
     */
    @TableField("total_vol")
    private Double totalVol;

    /**
     * 信号强度
     */
    @TableField("signal_dbm")
    private Integer signalDbm;

    @TableField(exist = false)
    private Integer status;

    @TableField(exist = false)
    private String cardId;

    @TableField(exist = false)
    private List<BatteryGroup> batteryGroups;
}
