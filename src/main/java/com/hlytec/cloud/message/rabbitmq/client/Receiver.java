package com.hlytec.cloud.message.rabbitmq.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @description: Receiver
 * @author: zero
 * @date: 2021/5/28 10:07
 */
@Slf4j
@Component
public class Receiver {

    /*@RabbitListener(queues = {"system-alarm"})
    @RabbitHandler
    public void processMsg(String msg) {
        log.info("receive msg from topic queue:{}", msg);
    }*/
}
