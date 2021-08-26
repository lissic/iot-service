package com.hlytec.cloud.biz.operation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hlytec.cloud.message.mqtt.config.MqttGateway;

/**
 * @description: SendCmd
 * @author: zero
 * @date: 2021/5/27 14:54
 */
@RestController
public class SendCmd {

    @Autowired
    private MqttGateway gateway;

    @PostMapping("/send")
    public void sendCmd(@RequestBody String payload) {
        JSONObject jsonObject = JSONObject.parseObject(payload);
        gateway.sendMessage2Mqtt(jsonObject.toJSONString());
    }
}
