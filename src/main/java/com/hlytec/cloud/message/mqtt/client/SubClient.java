package com.hlytec.cloud.message.mqtt.client;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;

import com.hlytec.cloud.biz.device.process.context.ProcessMqttMsgContext;
import com.hlytec.cloud.message.mqtt.config.MqttServerConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: SubClient
 * @author: zero
 * @date: 2021/5/27 11:43
 */
@Slf4j
@Configuration
public class SubClient {

    @Resource
    private MqttServerConfig mqttConfig;

    @Resource
    private MqttPahoClientFactory mqttClientFactory;

    @Resource
    private ProcessMqttMsgContext context;

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handlerMsg() {
        return message -> {
            try {
                // 使用策略模式将消息分发处理,主要接收来自/ToSer/**数据
                MessageHeaders headers = message.getHeaders();
                Object o = headers.get(MqttHeaders.RECEIVED_TOPIC);
                String msg = message.getPayload().toString();
                context.processMsg(o.toString(),msg);
                log.info("receive message from {}, message is: {}", o.toString(), message);
            } catch (MessagingException | NullPointerException ex) {
                log.error("receive message error:{}", ex.getMessage());
            }
        };
    }

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter("java-subscribe-",
                        mqttClientFactory, mqttConfig.getDefaultTopic().trim().split(","));
        adapter.setCompletionTimeout(mqttConfig.getCompletionTimeout());
        adapter.setConverter(new DefaultPahoMessageConverter());
        // 设置服务质量
        // 0 最多一次，数据可能丢失;
        // 1 至少一次，数据可能重复;
        // 2 只有一次，有且只有一次;最耗性能
        adapter.setQos(2);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }
}
