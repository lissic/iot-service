package com.hlytec.cloud.biz.device.model.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.hlytec.cloud.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @description: DeviceConfig
 * @author: zero
 * @date: 2021/5/31 11:43
 */
@Setter
@Getter
@SuperBuilder
@TableName("net_device_config")
public class DeviceConfig extends BaseEntity<DeviceConfig> {
    public DeviceConfig() {
    }

    @TableField("device_id")
    private String deviceId;

    /**
     * 配置类型：0-网络配置;1-充电配置;2-放电配置;3-监控配置;4-软件配置;5-温度配置;99-其他配置
     */
    @TableField("config_type")
    private Integer configType;

    /**
     * json格式数据
     */
    @TableField(value = "config_content", typeHandler = FastjsonTypeHandler.class)
    private JSONObject configContent;

    @TableField(exist = false)
    private String cardId;
}
