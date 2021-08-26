package com.hlytec.cloud.biz.device.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hlytec.cloud.common.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

/**
 * @author zero
 * @description DeviceSoft
 * @date 2021/7/29 15:04
 */
@Setter
@Getter
@SuperBuilder
@TableName("net_device_soft")
@NoArgsConstructor
public class DeviceSoft extends BaseEntity<DeviceSoft> {

    @NotEmpty(message = "固件名称不能为空")
    @TableField("name")
    private String name;
    @NotEmpty(message = "固件版本不能为空")
    @TableField("version")
    private String version;
    @TableField("file_path")
    private String filePath;
    /**
     * 升级方式：0-主动推送；1-设备拉取
     */
    @TableField("utype")
    private Integer upgradeType;
}
