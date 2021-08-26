package com.hlytec.cloud.biz.report.model.po;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zero
 * @description DeviceAlarm
 * @date 2021/8/6 16:18
 */
@Data
@NoArgsConstructor
public class DeviceAlarm {
    private String name;
    private String station;
    private Integer status;
    private Integer num;
}
