package com.hlytec.cloud.biz.report.model.vo;

import com.hlytec.cloud.biz.report.model.po.*;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

/**
 * @description: ServerInfo
 * @author: zero
 * @date: 2021/4/27 10:02
 */
@Data
@Builder
public class ServerInfo {
    /**
     * CPU相关信息
     */
    private CpuInfo cpu = new CpuInfo();

    /**
     * 內存相关信息
     */
    private MemInfo mem = new MemInfo();

    /**
     * JVM相关信息
     */
    private JvmInfo jvm = new JvmInfo();

    /**
     * 服务器相关信息
     */
    private SysInfo sys = new SysInfo();

    /**
     * 磁盘相关信息
     */
    private List<DiskInfo> disks = new LinkedList<>();
}
