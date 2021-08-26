package com.hlytec.cloud.biz.system.station.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hlytec.cloud.common.entity.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * sys_station 站点表
 * @author zero
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@TableName("sys_station")
public class Station extends BaseEntity<Station> {

    /**
     * 所属区域
     */
    @NotEmpty(message = "所属区域不能为空")
    @TableField("area")
    private String area;

    /**
     * 站点名称
     */
    @NotEmpty(message = "站点名称不能为空")
    @TableField("name")
    private String name;

    /**
     * 站点编码
     */
    @TableField("code")
    private String code;


    @TableField("type")
    private Integer type;

    @TableField("longitude")
    private Double longitude;

    @TableField("latitude")
    private Double latitude;

    /**
     * 子节点列表
     */
    @TableField(exist = false)
    private List<Station> childNode;

}
