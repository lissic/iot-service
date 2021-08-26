package com.hlytec.cloud.message.mqtt.config;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @description: MqttGateway
 * @author: zero
 * @date: 2021/5/27 11:04
 */

@Component
@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MqttGateway {
    /**
     * 向默认的 topic 发送消息
     * @param payload payload
     */
    void sendMessage2Mqtt(@Payload String payload);

    /**
     * 向指定的 topic 发送消息
     * @param payload payload
     * @param topic topic
     */
    void sendMessage2Mqtt(String payload, @Header(MqttHeaders.TOPIC) String topic);

    /**
     * 向指定的 topic 发送消息，并指定服务质量参数
     * @param topic topic
     * @param qos qos
     * @param payload payload
     */
    void sendMessage2Mqtt(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, String payload);

}
