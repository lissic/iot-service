package com.hlytec.cloud.biz.device.model.vo;

import com.hlytec.cloud.biz.device.model.entity.Device;
import lombok.Data;

import java.util.List;

/**
 * @author zero
 * @description DeviceRecordVo
 * @date 2021/8/4 10:16
 */
@Data
public class DeviceRecordVo {
    private String softId;
    private List<Device> devices;
}
