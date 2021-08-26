package com.hlytec.cloud.biz.device.process.strategy.impl;

import com.hlytec.cloud.biz.device.process.strategy.ProcessMqttMsgStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @description: DefaultHandler
 * @author: zero
 * @date: 2021/7/12 16:10
 */
@Slf4j
@Component("mqtt-test")
public class DefaultHandler implements ProcessMqttMsgStrategy {
    @Override
    public void processMqttMsg(String message) {
        log.info("receive msg from mqtt-test,{}", message);
    }
}
