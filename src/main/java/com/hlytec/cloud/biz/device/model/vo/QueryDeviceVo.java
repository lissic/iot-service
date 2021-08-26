package com.hlytec.cloud.biz.device.model.vo;

import com.hlytec.cloud.common.entity.BaseQueryEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @description: QueryDeviceVo
 * @author: zero
 * @date: 2021/6/29 9:42
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryDeviceVo extends BaseQueryEntity {

    private String cardId;

    private String name;

    private String devType;

    private String area;

    private List<String> stationIds;

    /**
     * 电压级别:0-220V;1-110V;2-48V
     */
    private Integer volLevel;

    /**
     * 放电类型: 0-逆变放电;1-升压放电;2-PTC放电;3-第三方放电
     */
    private Integer dischargeType;

    /**
     * 设备状态：0-离线;1-在线
     */
    private Integer status;
}
