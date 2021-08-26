package com.hlytec.cloud.biz.device.model.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @description: QueryBatteryGroupVo
 * @author: zero
 * @date: 2021/6/30 10:02
 */
@Data
public class QueryBatteryGroupVo {

    /**
     * 设备ID
     */
    private String deviceId;
    /**
     * 电池组名称
     */
    private String name;

    /**
     * 电池组序号
     */
    private String groupId;

    /**
     * 状态：0-监控中;1-无效;99-其他
     */
    private Integer status;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 生产商
     */
    private String manufacturer;

    /**
     * 电池类型
     */
    private Integer type;

    /**
     * 规格型号
     */
    private String model;

    /**
     * 生产批号
     */
    private String batchNum;

    /**
     * 安装日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date installTime;
}
