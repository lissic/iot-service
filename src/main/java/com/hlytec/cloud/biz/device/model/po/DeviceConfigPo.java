package com.hlytec.cloud.biz.device.model.po;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

/**
 * @description: DeviceConfigPo
 * @author: zero
 * @date: 2021/6/29 17:46
 */
@Data
public class DeviceConfigPo {
    private String id;

    /**
     * 配置类型：0-网络配置;1-充电配置;2-放电配置;3-监控配置;4-软件配置;99-其他配置
     */
    private Integer configType;

    /**
     * json格式数据
     */
    private JSONObject configContent;
}
