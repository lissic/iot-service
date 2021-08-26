package com.hlytec.cloud.biz.task.model.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hlytec.cloud.common.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

/**
 * @description: Task
 * @author: zero
 * @date: 2021/6/2 17:47
 */
@Setter
@Getter
@SuperBuilder
@TableName("net_task")
public class Task extends BaseEntity<Task> {
    Task(){}

    @NotEmpty(message = "任务名不能为空")
    @TableField("name")
    private String name;

    /**
     * 任务描述
     */
    @NotEmpty(message = "任务描述不能为空")
    @TableField("description")
    private String description;

    /**
     * 调用目标
     */
    @NotEmpty(message = "任务调用目标不能为空")
    @TableField("invoke_target")
    private String invokeTarget;

    /**
     * Cron表达式
     */
    @NotEmpty(message = "执行表达式不能为空")
    @TableField("cron_expression")
    private String expression;

    /**
     * 任务状态：0-暂停;1-运行中
     */
    @TableField("status")
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("last_exec_time")
    private Date lastExecTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("next_exec_time")
    private Date nextExecTime;
}
