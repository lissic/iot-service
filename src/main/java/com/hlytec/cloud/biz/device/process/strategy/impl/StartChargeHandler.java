package com.hlytec.cloud.biz.device.process.strategy.impl;

import org.springframework.stereotype.Component;

import com.hlytec.cloud.biz.device.process.strategy.ProcessMqttMsgStrategy;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: 启动充电
 * @author: zero
 * @date: 2021/7/13 10:19
 */
@Slf4j
@Component("start-charge")
public class StartChargeHandler implements ProcessMqttMsgStrategy {
    @Override
    public void processMqttMsg(String message) {
        // TODO 请求成功后将对应日志信息入库
    }
}
