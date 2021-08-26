package com.hlytec.cloud.biz.asset.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hlytec.cloud.common.entity.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @description: Asset
 * @author: JackChen
 * @date: 2021/5/26 10:09
 */
@Data
@Getter
@Setter
@SuperBuilder
@TableName("net_assets")
public class Asset extends BaseEntity<Asset>{

    public Asset() {
    }
    @NotEmpty(message = "资产编码不能为空")
    @TableField("asset_code")
    private String assetCode;

    @TableField("category")
    private Integer category;

    @NotEmpty(message = "资产名称不能为空")
    @TableField("name")
    private String name;

    @NotEmpty(message = "资产型号不能为空")
    @TableField("model")
    private String model;

    @NotEmpty(message = "资产来源不能为空")
    @TableField("source")
    private String source;

    @NotEmpty(message = "供应商不能为空")
    @TableField("supplier")
    private String supplier;

    @TableField("brand")
    private String brand;

    /**
     * 0-闲置;1-在用;2-借用;3-维修;4-报废;5-停用
     */
    @TableField("status")
    private Integer status;

    @TableField("department_id")
    private String departmentId;

    @TableField("department_name")
    private String departmentName;

    @TableField("use_person_id")
    private String usePersonId;

    @NotEmpty(message = "使用人不能为空")
    @TableField("use_person_name")
    private String usePersonName;

    @NotEmpty(message = "资产位置不能为空")
    @TableField("location")
    private String location;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @TableField("procurement_time")
    private Date procurementTime;

    @TableField("photo")
    private String photo;

}
