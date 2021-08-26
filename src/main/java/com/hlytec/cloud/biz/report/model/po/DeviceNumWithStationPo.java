package com.hlytec.cloud.biz.report.model.po;

import com.hlytec.cloud.biz.device.model.entity.Device;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description: DeviceNumWithAreaPo
 * @author: zero
 * @date: 2021/7/8 14:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceNumWithStationPo {
    private String stationId;
    private String stationName;
    private String area;
    private Double longitude;
    private Double latitude;
    private List<Device> devices;
}
