package com.hlytec.cloud.biz.system.role.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hlytec.cloud.biz.system.menu.model.entity.Menu;
import com.hlytec.cloud.common.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.List;

/**
 * @description: Role 角色
 * @author: zero
 * @date: 2021/4/19 14:23
 */

@Setter
@Getter
@SuperBuilder
@TableName("sys_role")
public class Role extends BaseEntity<Role> {

    public Role() {}

    @TableField("role")
    private String role;

    @TableField("description")
    private String description;

    @TableField("available")
    private Boolean available = Boolean.TRUE;

    @TableField("is_admin")
    private Boolean isAdmin = Boolean.FALSE;

    /**
     * 菜单集合
     */
    @TableField(exist = false)
    private List<Menu> menus;

}
