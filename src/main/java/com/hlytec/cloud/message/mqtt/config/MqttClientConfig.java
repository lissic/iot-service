package com.hlytec.cloud.message.mqtt.config;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import javax.annotation.Resource;

/**
 * @description: MqttSubConfig
 * @author: zero
 * @date: 2021/5/27 10:46
 */
@Slf4j
@IntegrationComponentScan
@Configuration
public class MqttClientConfig {

    @Resource
    private MqttServerConfig mqttConfig;

    @Bean(value = "getMqttConnectOptions")
    public MqttConnectOptions getMqttConnectOptions() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setUserName(mqttConfig.getUsername());
        mqttConnectOptions.setPassword(mqttConfig.getPassword().toCharArray());
        mqttConnectOptions.setServerURIs(new String[]{mqttConfig.getHostUrl()});
        //cleanSession 设为 true;当客户端掉线时;服务器端会清除 客户端session;重连后 客户端会有一个新的session,cleanSession
        // 设为false，客户端掉线后 服务器端不会清除session，当重连后可以接收之前订阅主题的消息。当客户端上线后会接受到它离线的这段时间的消息
        mqttConnectOptions.setCleanSession(true);
        // 设置会话心跳时间 单位为秒
        mqttConnectOptions.setKeepAliveInterval(10);
        return mqttConnectOptions;
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectOptions());
        return factory;
    }

}
