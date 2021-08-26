package com.hlytec.cloud.message.rabbitmq.client;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: Sender
 * @author: zero
 * @date: 2021/5/28 10:04
 */
@Component
public class Sender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String exchange, String routingKey, String msg) {
        rabbitTemplate.convertAndSend(exchange,routingKey, msg);
    }
}
