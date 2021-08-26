package com.hlytec.cloud.biz.report.model.po;

import lombok.Data;

/**
 * @description: SysInfo
 * @author: zero
 * @date: 2021/4/27 10:02
 */
@Data
public class SysInfo {
    /**
     * 服务器名称
     */
    private String sysName;

    /**
     * 服务器Ip
     */
    private String sysIp;

    /**
     * 操作系统
     */
    private String osName;

    /**
     * 系统架构
     */
    private String osArch;
}
