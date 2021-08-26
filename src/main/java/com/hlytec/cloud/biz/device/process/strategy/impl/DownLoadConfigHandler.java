package com.hlytec.cloud.biz.device.process.strategy.impl;

import com.hlytec.cloud.biz.system.user.model.entity.User;
import com.hlytec.cloud.biz.system.user.service.UserService;
import com.hlytec.cloud.biz.system.user.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.hlytec.cloud.biz.device.model.entity.DeviceLog;
import com.hlytec.cloud.biz.device.model.entity.DeviceOperation;
import com.hlytec.cloud.biz.device.process.strategy.ProcessMqttMsgStrategy;
import com.hlytec.cloud.biz.device.service.DeviceLogService;
import com.hlytec.cloud.common.constants.SysConstants;
import com.hlytec.cloud.message.rabbitmq.client.Sender;
import com.hlytec.cloud.message.rabbitmq.config.RabbitConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: 获取配置信息
 * @author: zero
 * @date: 2021/7/13 9:43
 */
@Slf4j
@Component("/ToSer/RYX/5_GetBatteryCfgInfo")
public class DownLoadConfigHandler implements ProcessMqttMsgStrategy {
    @Autowired
    private Sender sender;
    @Autowired
    private DeviceLogService logService;
    @Autowired
    private UserService userService;

    @Override
    public void processMqttMsg(String message) {
        log.info("receive message from config,{}", message);
        // 推送结果至前端
        String guid = JSONObject.parseObject(message).getString("Guid");
        String cardId = guid.substring(17);
        DeviceOperation operation = DeviceOperation.getInstance();
        operation.setMessage(message);
        operation.setOperationType(5);
        operation.setCardId(cardId);
        sender.send(RabbitConfig.DEFAULT_EXCHANGE,RabbitConfig.DEVICE_OPERATION,JSONObject.toJSONString(operation));
        // 请求成功后更新操作日志
        String userId = UserUtil.getCurrentUserId();
        User user = userService.get(userId);
        DeviceLog deviceLog = logService.findOneWithResultNull(DeviceLog.builder().cardId(cardId).operationType(5)
                .operationPerson(user.getLoginName()).build());
        deviceLog.setResult(SysConstants.SUCCESS_CODE);
        logService.updateById(deviceLog);
    }
}
