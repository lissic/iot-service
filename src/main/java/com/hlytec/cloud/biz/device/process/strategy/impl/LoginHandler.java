package com.hlytec.cloud.biz.device.process.strategy.impl;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.*;

import com.hlytec.cloud.biz.device.model.entity.Device;
import com.hlytec.cloud.biz.device.service.DeviceService;
import com.hlytec.cloud.common.exception.NetServiceException;
import com.hlytec.cloud.common.thread.MyThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.hlytec.cloud.biz.device.process.strategy.ProcessMqttMsgStrategy;
import com.hlytec.cloud.message.rabbitmq.client.Sender;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: LoginHandler
 * @author: zero
 * @date: 2021/7/12 15:48
 */
@Slf4j
@Component("/ToSer/RYX/0_Login")
public class LoginHandler implements ProcessMqttMsgStrategy {

    @Autowired
    private DeviceService deviceService;

    @Override
    public void processMqttMsg(String message) {
        log.info("receive message from login,{}", message);
        String guid = JSONObject.parseObject(message).getString("Guid");
        String cardId = guid.substring(17);
        CompletableFuture.runAsync(() -> {
            Device dev = deviceService.getDeviceByCardId(cardId);
            // 如果设备状态为离线，则更新为在线，其他状态不更新，只要保持心跳即可
            if(Objects.nonNull(dev) && dev.getStatus() == 0) {
                log.info("正在更新设备状态。。。。");
                dev.setStatus(1);
                deviceService.updateById(dev);
            } else if(Objects.nonNull(dev)) {
                // 更新设备上次连接时间
                dev.setLastConnTime(new Date());
                deviceService.updateById(dev);
            }
        }, MyThreadPoolExecutor.getThreadPool()).exceptionally((exp)->{
            throw new NetServiceException(exp);
        }).join();
    }
}
