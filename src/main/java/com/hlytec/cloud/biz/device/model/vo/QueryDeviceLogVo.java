package com.hlytec.cloud.biz.device.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @description: QueryDeviceLogVo
 * @author: zero
 * @date: 2021/7/22 9:29
 */
@Data
public class QueryDeviceLogVo {
    private String deviceId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
}
