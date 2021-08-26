package com.hlytec.cloud.biz.device.process.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hlytec.cloud.biz.device.process.strategy.ProcessMqttMsgStrategy;

/**
 * @description: ProcessMqttMsgContext
 * @author: zero
 * @date: 2021/7/12 15:44
 */
@Service
public class ProcessMqttMsgContext {
    private final Map<String, ProcessMqttMsgStrategy> strategyMap = new ConcurrentHashMap<>();

    @Autowired
    public ProcessMqttMsgContext(Map<String, ProcessMqttMsgStrategy > strategyMap) {
        this.strategyMap.clear();
        strategyMap.forEach(this.strategyMap::put);
    }
    public void processMsg(String topic, String message) {
        strategyMap.get(topic).processMqttMsg(message);
    }
}
