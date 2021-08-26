package com.hlytec.cloud.message.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: RabbitConfig
 * @author: zero
 * @date: 2021/5/28 9:51
 */
@Configuration
public class RabbitConfig {

    public static final String SYSTEM_ALARM = "system-alarm";
    public static final String DEVICE_OPERATION = "device-operation";
    public static final String DEFAULT_EXCHANGE = "topicExchange";

    @Bean
    Queue topicQueue() {
        return new Queue(SYSTEM_ALARM);
    }

    @Bean
    Queue deviceQueue() {
        return new Queue(DEVICE_OPERATION);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(DEFAULT_EXCHANGE);
    }

    @Bean
    Binding bindExchange(@Qualifier("topicQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(SYSTEM_ALARM);
    }

    @Bean
    Binding bindExchange2(@Qualifier("deviceQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DEVICE_OPERATION);
    }
}
