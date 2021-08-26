package com.hlytec.cloud.biz.device.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hlytec.cloud.common.entity.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author zero
 * @description DeviceUpgradeRecord
 * @date 2021/7/29 15:07
 */
@Setter
@Getter
@SuperBuilder
@TableName("net_upgrade_record")
@NoArgsConstructor
public class DeviceUpgradeRecord extends BaseEntity<DeviceUpgradeRecord> {
    @TableField("soft_id")
    private String softId;
    @TableField("device_id")
    private String deviceId;
    @TableField("device_name")
    private String deviceName;
    /**
     * 升级状态：0-未开始;1-升级中；2-升级完成；3-升级失败
     */
    @TableField("status")
    private Integer status;
    @TableField("progress")
    private int progress;
}
