package com.hlytec.cloud.biz.device.process.strategy.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.hlytec.cloud.biz.device.model.entity.Battery;
import com.hlytec.cloud.biz.device.model.entity.BatteryGroup;
import com.hlytec.cloud.biz.device.model.entity.Device;
import com.hlytec.cloud.biz.device.model.entity.DeviceMonitor;
import com.hlytec.cloud.biz.device.process.strategy.ProcessMqttMsgStrategy;
import com.hlytec.cloud.biz.device.service.BatteryGroupService;
import com.hlytec.cloud.biz.device.service.BatteryService;
import com.hlytec.cloud.biz.device.service.DeviceMonitorService;
import com.hlytec.cloud.biz.device.service.DeviceService;
import com.hlytec.cloud.common.exception.NetServiceException;
import com.hlytec.cloud.common.result.ResultEnum;
import com.hlytec.cloud.message.mqtt.config.MqttGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: 基础信息上报
 * @author: zero
 * @date: 2021/7/13 10:19
 */
@Slf4j
@Component("/ToSer/RYX/2_ReportBatteryBasicInfo")
public class ReportBasicHandler implements ProcessMqttMsgStrategy {
    public final String topic = "/ToEqu/RYX/2_ReportBatteryBasicInfo";

    @Autowired
    private DeviceMonitorService monitorService;

    @Autowired
    private MqttGateway gateway;

    @Override
    public void processMqttMsg(String message) {
        log.info("report message from basicInfo,{}", message);
        // 根据事件的信息更新设备的信息
        String guid = monitorService.updateDeviceByMsg(message);
        // 发送响应到mqtt
        if (StringUtils.isNotEmpty(guid)) {
            JSONObject json = new JSONObject();
            json.put("Guid",guid);
            gateway.sendMessage2Mqtt(json.toJSONString(),topic);
        }
    }
}
