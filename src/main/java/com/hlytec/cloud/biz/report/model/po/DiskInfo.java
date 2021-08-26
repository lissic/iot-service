package com.hlytec.cloud.biz.report.model.po;

import lombok.Data;

/**
 * @description: DiskInfo
 * @author: zero
 * @date: 2021/4/27 10:02
 */
@Data
public class DiskInfo {
    /**
     * 盘符路径
     */
    private String dirName;

    /**
     * 盘符类型
     */
    private String sysTypeName;

    /**
     * 文件类型
     */
    private String typeName;

    /**
     * 总大小
     */
    private String total;

    /**
     * 剩余大小
     */
    private String free;

    /**
     * 已经使用量
     */
    private String used;

    /**
     * 资源的使用率
     */
    private double usage;
}
