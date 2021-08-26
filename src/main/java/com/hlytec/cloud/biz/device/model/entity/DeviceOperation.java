package com.hlytec.cloud.biz.device.model.entity;

import lombok.Data;

/**
 * @description: 设备操作结果 单例
 * @author: zero
 * @date: 2021/7/13 9:18
 */
@Data
public class DeviceOperation {
    private volatile static DeviceOperation instance;
    private DeviceOperation() {}
    public static DeviceOperation getInstance() {
        if(instance == null) {
            synchronized (DeviceOperation.class) {
                if(instance == null) {
                    instance = new DeviceOperation();
                }
            }
        }
        return instance;
    }
    /**
     * 板卡编号/IMEI编号(15位)
     */
    private String cardId;
    /**
     * 消息体(操作的结果)
     */
    private String message;
    /**
     * 操作类型
     * 0-登录,心跳
     * 1-下发网络配置
     * 2-上报基础信息
     * 3-上报事件信息
     * 4-获取基本信息
     * 5-获取配置信息
     * 6-下发现场参数
     * 7-下发温度参数
     * 8-下发充电参数
     * 9-下发时间参数
     * 10-下发核容参数
     * 11-核容
     * 12-充电
     */
    private Integer operationType;
}
