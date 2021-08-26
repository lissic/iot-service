package com.hlytec.cloud.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @description: BaseEntity
 * @author: zero
 * @date: 2021/4/27 15:26
 */

@Setter
@Getter
@SuperBuilder
public class BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public BaseEntity(){}

    /**
     * 主键ID，使用不带下划线的UUID
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
    private String updateUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField("remarks")
    private String remarks;

    @TableLogic
    @TableField("is_delete")
    private Boolean deleted;

}
