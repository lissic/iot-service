package com.hlytec.cloud.message.mqtt.client;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import com.hlytec.cloud.message.mqtt.config.MqttServerConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: PubClient
 * @author: zero
 * @date: 2021/5/27 14:48
 */
@Slf4j
@Configuration
public class PubClient {

    @Resource
    private MqttPahoClientFactory mqttClientFactory;

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler outbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("java-publish-", mqttClientFactory);
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic("mqtt-test");
        return messageHandler;
    }
}
