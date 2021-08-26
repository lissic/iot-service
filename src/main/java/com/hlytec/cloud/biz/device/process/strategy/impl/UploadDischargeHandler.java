package com.hlytec.cloud.biz.device.process.strategy.impl;

import com.alibaba.fastjson.JSONObject;
import com.hlytec.cloud.biz.device.model.entity.DeviceLog;
import com.hlytec.cloud.biz.device.model.entity.DeviceOperation;
import com.hlytec.cloud.biz.device.service.DeviceLogService;
import com.hlytec.cloud.biz.system.user.model.entity.User;
import com.hlytec.cloud.biz.system.user.service.UserService;
import com.hlytec.cloud.biz.system.user.utils.UserUtil;
import com.hlytec.cloud.common.constants.SysConstants;
import com.hlytec.cloud.message.rabbitmq.client.Sender;
import com.hlytec.cloud.message.rabbitmq.config.RabbitConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hlytec.cloud.biz.device.process.strategy.ProcessMqttMsgStrategy;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: 上传核容参数
 * @author: zero
 * @date: 2021/7/13 10:19
 */
@Slf4j
@Component("/ToSer/RYX/a_SetBatteryConfirmCapacityParam")
public class UploadDischargeHandler implements ProcessMqttMsgStrategy {
    @Autowired
    private Sender sender;
    @Autowired
    private DeviceLogService logService;
    @Autowired
    private UserService userService;

    @Override
    public void processMqttMsg(String message) {
        log.info("upload message from discharge,{}", message);
        // 推送结果至前端
        String guid = JSONObject.parseObject(message).getString("Guid");
        String cardId = guid.substring(17);
        DeviceOperation operation = DeviceOperation.getInstance();
        operation.setMessage(SysConstants.SUCCESS);
        operation.setOperationType(10);
        operation.setCardId(cardId);
        sender.send(RabbitConfig.DEFAULT_EXCHANGE,RabbitConfig.DEVICE_OPERATION, JSONObject.toJSONString(operation));
        // 请求成功后更新操作日志
        String userId = UserUtil.getCurrentUserId();
        User user = userService.get(userId);
        DeviceLog deviceLog = logService.findOneWithResultNull(DeviceLog.builder().cardId(cardId).operationType(10)
                .operationPerson(user.getLoginName()).build());
        deviceLog.setResult(SysConstants.SUCCESS_CODE);
        logService.updateById(deviceLog);
    }
}
