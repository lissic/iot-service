package com.hlytec.cloud.biz.system.user.model.entity;

import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hlytec.cloud.biz.system.role.model.entity.Role;
import com.hlytec.cloud.common.entity.BaseEntity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @description: User
 * @author: zero
 * @date: 2021/4/16 10:17
 */
@Setter
@Getter
@SuperBuilder
@TableName("sys_user")
public class User extends BaseEntity<User> {

    public User() {}

    @TableField("nick_name")
    private String nickName;

    @TableField("area")
    private String area;

    /**
     * 性别：0-女;1-男
     */
    @TableField("sex")
    private Integer sex;

    @TableField("password")
    private String password;

    @TableField("email")
    private String email;

    @TableField("phone")
    private String phone;

    @TableField("photo")
    private String photo;

    @TableField("introduction")
    private String introduction;

    @TableField("login_name")
    private String loginName;

    /**
     * 1:正常状态；2：用户被锁定.
     */
    @TableField("status")
    private Integer status;

    @TableField("dept_id")
    private String deptId;

    /**
     * 角色集合
     */
    @TableField(exist = false)
    private List<Role> roles;

    @TableField(exist = false)
    private String deptName;
}
