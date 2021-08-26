package com.hlytec.cloud.biz.device.process.strategy;

/**
 * @description: ProcessMqttMsgStrategy
 * @author: zero
 * @date: 2021/7/12 15:43
 */
public interface ProcessMqttMsgStrategy {
    /**
     * 处理mqtt消息
     * @param message message
     */
    void processMqttMsg(String message);
}
