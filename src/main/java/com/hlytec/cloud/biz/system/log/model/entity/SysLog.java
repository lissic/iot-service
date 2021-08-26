package com.hlytec.cloud.biz.system.log.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hlytec.cloud.common.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @description: Log
 * @author: zero
 * @date: 2021/4/27 17:44
 */

@Setter
@Getter
@SuperBuilder
@TableName("sys_log")
public class SysLog extends BaseEntity<SysLog> {
    public SysLog() {}
    /**
     * 日志标题
     */
    @TableField("title")
    private String title;

    /**
     * 请求地址
     */
    @TableField("request_url")
    private String requestUrl;

    /**
     * 日志类型
     */
    @TableField("type")
    private String type;

    /**
     * 业务类型
     */
    @TableField("biz_type")
    private String bizType;

    /**
     * 日志内容
     */
    @TableField("content")
    private String content;

    /**
     * 客户端ip
     */
    @TableField("client_ip")
    private String clientIp;

    /**
     * 是否异常：0正常，1异常
     */
    @TableField("is_exception")
    private int isException;

    /**
     * 执行时间，单位毫秒
     */
    @TableField("execute_time")
    private Long executeTime;

    /**
     * 操作者
     */
    @TableField("operator")
    private String operator;

}
