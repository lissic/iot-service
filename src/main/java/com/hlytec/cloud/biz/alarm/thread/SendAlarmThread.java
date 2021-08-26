package com.hlytec.cloud.biz.alarm.thread;

import com.hlytec.cloud.message.rabbitmq.client.Sender;

/**
 * @description: SendAlarmThread
 * @author: zero
 * @date: 2021/6/24 15:36
 */
public class SendAlarmThread implements Runnable{

    private final Sender sender;
    private final String msg;
    private final String exchange;
    private final String routingKey;

    public SendAlarmThread(Sender sender, String exchange, String routingKey, String msg) {
        this.sender = sender;
        this.msg = msg;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }
    @Override
    public void run() {
        sender.send(exchange,routingKey,msg);
    }
}
