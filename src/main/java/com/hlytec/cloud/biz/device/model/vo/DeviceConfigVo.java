package com.hlytec.cloud.biz.device.model.vo;

import com.hlytec.cloud.biz.device.model.entity.DeviceConfig;
import lombok.Data;

import java.util.List;

/**
 * @description: DeviceConfigVo
 * @author: JackChen
 * @date: 2021/6/9 15:44
 */
@Data
public class DeviceConfigVo {
    private List<DeviceConfig> configList;
}
