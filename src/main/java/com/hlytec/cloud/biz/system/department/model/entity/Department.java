package com.hlytec.cloud.biz.system.department.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hlytec.cloud.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @description: entity
 * @author: zero
 * @date: 2021/5/26 10:02
 */
@Setter
@Getter
@SuperBuilder
@TableName("sys_department")
public class Department extends BaseEntity<Department> {

    @TableField("parent_id")
    private String parentId;

    @TableField("parent_ids")
    private String parentIds;

    @NotEmpty(message = "部门名称不能为空")
    @TableField("name")
    private String name;

    @NotEmpty(message = "部门地址不能为空")
    @TableField("address")
    private String address;

    @NotEmpty(message = "邮政编码不能为空")
    @TableField("zip_code")
    private String zipCode;

    @NotEmpty(message = "负责人不能为空")
    @TableField("master")
    private String master;

    @NotEmpty(message = "电话不能为空")
    @TableField("phone")
    private String phone;

    @TableField("avaliable")
    private Boolean avaliable;

    @TableField(exist = false)
    private List<Department> childNode;

    public Department() {
    }
}
