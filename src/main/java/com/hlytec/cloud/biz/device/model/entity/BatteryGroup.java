package com.hlytec.cloud.biz.device.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hlytec.cloud.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

/**
 * @description: BatteryGroup
 * @author: zero
 * @date: 2021/5/31 13:50
 */
@Setter
@Getter
@SuperBuilder
@TableName("net_battery_group")
public class BatteryGroup extends BaseEntity<BatteryGroup> {

    public BatteryGroup() {}

    @TableField("device_id")
    private String deviceId;

    /**
     * 电池组名称
     */
    @TableField("name")
    private String name;

    /**
     * 电池组序号
     */
    @TableField("group_id")
    private String groupId;

    /**
     * 状态：0-监控中;1-无效;99-其他
     */
    @TableField("status")
    private Integer status;

    /**
     * 品牌
     */
    @TableField("brand")
    private String brand;

    /**
     * 生产商
     */
    @TableField("manufacturer")
    private String manufacturer;

    /**
     * 电池类型:0-铅酸蓄电池;1-UPS;2-磷酸铁锂蓄电池;3-超级蓄电池;99-其他
     */
    @TableField("type")
    private Integer type;

    /**
     * 规格型号
     */
    @TableField("model")
    private String model;

    /**
     * 电池个数
     */
    @TableField("battery_num")
    private Integer batteryNum;

    /**
     * 生产批号
     */
    @TableField("batch_num")
    private String batchNum;

    /**
     * 安装日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("install_time")
    private Date installTime;

    /**
     * 总电压
     */
    @TableField("total_voltage")
    private Double totalVol;

    /**
     * 总电流
     */
    @TableField("total_electricity")
    private Double totalElectric;

    /**
     * 总容量
     */
    @TableField("total_capacity")
    private Double totalCapacity;

    /**
     * 剩余容量
     */
    @TableField("remaining_capacity")
    private Double remainingCapacity;

    /**
     * 升电压
     */
    @TableField("boost_voltage")
    private Double boostVol;

    /**
     * 导电条连接状态
     */
    @TableField("emi_gasket_status")
    private String EMIGasketStatus;

    /**
     * 剩余可用时长
     */
    @TableField("remaining_usable_time")
    private String remainingUsableTime;

    @TableField(exist = false)
    private List<Battery> batteries;

}
