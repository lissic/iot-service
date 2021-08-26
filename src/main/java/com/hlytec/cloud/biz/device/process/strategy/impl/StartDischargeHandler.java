package com.hlytec.cloud.biz.device.process.strategy.impl;

import com.alibaba.fastjson.JSONObject;
import com.hlytec.cloud.biz.device.model.entity.Device;
import com.hlytec.cloud.biz.device.model.entity.DeviceLog;
import com.hlytec.cloud.biz.device.model.entity.DeviceOperation;
import com.hlytec.cloud.biz.device.service.DeviceLogService;
import com.hlytec.cloud.biz.device.service.DeviceService;
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
 * @description: 启动放电
 * @author: zero
 * @date: 2021/7/13 10:19
 */
@Slf4j
@Component("/ToSer/RYX/b_StartBatteryConfirmCapacity")
public class StartDischargeHandler implements ProcessMqttMsgStrategy {
    @Autowired
    private Sender sender;
    @Autowired
    private DeviceLogService logService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private UserService userService;

    @Override
    public void processMqttMsg(String message) {
        JSONObject json = JSONObject.parseObject(message);
        String guid = json.getString("Guid");
        String cardId = guid.substring(17);
        // 推送结果至前端
        DeviceOperation operation = DeviceOperation.getInstance();
        operation.setMessage(SysConstants.SUCCESS);
        operation.setOperationType(11);
        operation.setCardId(cardId);
        sender.send(RabbitConfig.DEFAULT_EXCHANGE,RabbitConfig.DEVICE_OPERATION,JSONObject.toJSONString(operation));
        // 请求成功后更新操作日志
        String userId = UserUtil.getCurrentUserId();
        User user = userService.get(userId);
        DeviceLog deviceLog = logService.findOneWithResultNull(DeviceLog.builder().cardId(cardId).operationType(11)
                .operationPerson(user.getLoginName()).build());
        deviceLog.setResult(SysConstants.SUCCESS_CODE);
        logService.updateById(deviceLog);
        // 更新数据库中状态--放电
        deviceService.updateById(Device.builder().id(deviceLog.getDeviceId()).status(4).build());
    }
}
