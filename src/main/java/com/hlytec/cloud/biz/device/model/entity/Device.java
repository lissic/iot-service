package com.hlytec.cloud.biz.device.model.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hlytec.cloud.biz.device.model.po.DeviceConfigPo;
import com.hlytec.cloud.common.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @description: Device
 * @author: zero
 * @date: 2021/5/31 11:08
 */
@Setter
@Getter
@SuperBuilder
@TableName("net_device")
public class Device extends BaseEntity<Device> {
    public Device() {}

    @NotEmpty(message = "板卡编号不能为空")
    @TableField("card_id")
    private String cardId;

    @NotEmpty(message = "设备名称不能为空")
    @TableField("name")
    private String name;

    @NotEmpty(message = "设备型号不能为空")
    @TableField("dev_type")
    private String devType;

    @NotEmpty(message = "所属站点不能为空")
    @TableField("station_id")
    private String stationId;

    @TableField("battery_grp_num")
    private Integer batteryGrpNum;

    /**
     * 电压级别:0-220V;1-110V;2-48V
     */
    @NotNull(message = "电压级别不能为空")
    @TableField("vol_level")
    private Integer volLevel;

    @TableField("battery_num")
    private Integer batteryNum;

    @TableField("charge_num")
    private Integer chargeNum;

    @TableField("discharge_num")
    private Integer dischargeNum;

    /**
     * 放电类型: 0-逆变放电;1-升压放电;2-PTC放电;3-第三方放电
     */
    @NotNull(message = "放电类型不能为空")
    @TableField("discharge_type")
    private Integer dischargeType;

    /**
     * 设备状态：0-离线;1-在线
     */
    @TableField("status")
    private Integer status;

    @TableField("monitor_num")
    private Integer monitorNum;

    @TableField("address")
    private String address;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("last_conn_time")
    private Date lastConnTime;

    @TableField(exist = false)
    private List<DeviceConfigPo> configList;

}
