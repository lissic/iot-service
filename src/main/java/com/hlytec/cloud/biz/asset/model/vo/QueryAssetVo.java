package com.hlytec.cloud.biz.asset.model.vo;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.hlytec.cloud.common.entity.BaseQueryEntity;
import lombok.Data;

import java.util.Date;

/**
 * @description: QueryAssetVo
 * @author: JackChen
 * @date: 2021/6/17 14:26
 */
@Data
public class QueryAssetVo extends BaseQueryEntity {

    private String id;
    private String assetCode;
    private Integer category;
    private String name;
    private String model;
    private String source;
    private String supplier;
    private String brand;
    /**
     * 0-闲置;1-在用;2-借用;3-维修;4-报废;5-停用
     */
    private Integer status;
    private String departmentId;
    private String departmentName;
    private String usePersonId;
    private String usePersonName;
    private String location;
    private Date procurementTime;
    private String photo;
}
