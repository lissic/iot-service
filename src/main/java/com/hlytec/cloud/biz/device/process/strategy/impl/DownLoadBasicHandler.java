package com.hlytec.cloud.biz.device.process.strategy.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSONObject;
import com.hlytec.cloud.biz.device.model.entity.DeviceLog;
import com.hlytec.cloud.biz.device.model.entity.DeviceMonitor;
import com.hlytec.cloud.biz.device.model.entity.DeviceOperation;
import com.hlytec.cloud.biz.device.process.strategy.ProcessMqttMsgStrategy;
import com.hlytec.cloud.biz.device.service.DeviceLogService;
import com.hlytec.cloud.biz.device.service.DeviceMonitorService;
import com.hlytec.cloud.biz.system.user.model.entity.User;
import com.hlytec.cloud.biz.system.user.service.UserService;
import com.hlytec.cloud.biz.system.user.utils.UserUtil;
import com.hlytec.cloud.common.constants.SysConstants;
import com.hlytec.cloud.message.rabbitmq.client.Sender;
import com.hlytec.cloud.message.rabbitmq.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 获取基本信息，同步设备状态
 * @author: zero
 * @date: 2021/7/13 9:43
 */
@Slf4j
@Component("/ToSer/RYX/4_GetBatteryBasicInfo")
public class DownLoadBasicHandler implements ProcessMqttMsgStrategy {
    @Autowired
    private Sender sender;
    @Autowired
    private DeviceMonitorService monitorService;
    @Autowired
    private DeviceLogService logService;
    @Autowired
    private UserService userService;

    @Override
    public void processMqttMsg(String message) {
        log.info("download message from basicInfo,{}", message);
        // 推送结果至前端
        String guid = monitorService.updateDeviceByMsg(message);
        String cardId = guid.substring(17);
        DeviceMonitor deviceMonitor = monitorService.getDeviceMonitorByCardId(cardId);
        DeviceOperation operation = DeviceOperation.getInstance();
        operation.setMessage(JSONObject.toJSONString(deviceMonitor));
        operation.setOperationType(4);
        operation.setCardId(cardId);
        sender.send(RabbitConfig.DEFAULT_EXCHANGE, RabbitConfig.DEVICE_OPERATION, JSONObject.toJSONString(operation));
        // 请求成功后更新操作日志
        String userId = UserUtil.getCurrentUserId();
        User user = userService.get(userId);
        DeviceLog deviceLog = logService.findOneWithResultNull(DeviceLog.builder().cardId(cardId).operationType(4)
            .operationPerson(user.getLoginName()).build());
        deviceLog.setResult(SysConstants.SUCCESS_CODE);
        logService.updateById(deviceLog);
    }
}
