package com.hlytec.cloud.biz.system.menu.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hlytec.cloud.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

/**
 * @description: Permission 权限
 * @author: zero
 * @date: 2021/4/19 14:25
 */

@Setter
@Getter
@SuperBuilder
@TableName("sys_menu")
public class Menu extends BaseEntity<Menu> {

    public Menu(){}

    @NotEmpty(message = "菜单名称不能为空")
    @TableField("name")
    private String name;

    @NotEmpty(message = "资源类型不能为空")
    @TableField("resource_type")
    private String resourceType;

    @TableField("permission")
    private String permission;

    @NotEmpty(message = "菜单路径不能为空")
    @TableField("url")
    private String url;

    @TableField("parent_id")
    private String parentId;

    @TableField("parent_ids")
    private String parentIds;

    @TableField("available")
    private Boolean available = Boolean.TRUE;

}
