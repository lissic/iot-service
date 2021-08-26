package com.hlytec.cloud.biz.device.process.strategy.impl;

import com.alibaba.fastjson.JSONObject;
import com.hlytec.cloud.biz.alarm.model.entity.Alarm;
import com.hlytec.cloud.biz.alarm.service.AlarmService;
import com.hlytec.cloud.biz.device.model.entity.Battery;
import com.hlytec.cloud.biz.device.model.entity.Device;
import com.hlytec.cloud.biz.device.service.BatteryService;
import com.hlytec.cloud.biz.device.service.DeviceService;
import com.hlytec.cloud.common.exception.NetServiceException;
import com.hlytec.cloud.common.result.ResultEnum;
import com.hlytec.cloud.message.mqtt.config.MqttGateway;
import com.hlytec.cloud.message.rabbitmq.client.Sender;
import com.hlytec.cloud.message.rabbitmq.config.RabbitConfig;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hlytec.cloud.biz.device.process.strategy.ProcessMqttMsgStrategy;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @description: 事件上报
 * @author: zero
 * @date: 2021/7/13 10:19
 */
@Slf4j
@Component("/ToSer/RYX/3_ReportBatteryEventInfo")
public class ReportEventHandler implements ProcessMqttMsgStrategy {
    public final String topic = "/ToEqu/RYX/3_ReportBatteryEventInfo";
    /**
     * 异常告警状态
      */
    public final List<Integer> alarmStatus = Arrays.asList(1,3,5,9,10,12,13,15,16,17,18,19,21);

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private BatteryService batteryService;
    @Autowired
    private AlarmService alarmService;
    @Autowired
    private MqttGateway gateway;
    @Autowired
    private Sender sender;

    @Override
    public void processMqttMsg(String message) {
        // 根据事件的信息更新设备的状态
        String guid = updateDeviceAndBatteryStatusByEvent(message);
        // 发送响应到mqtt
        if (StringUtils.isNotEmpty(guid)) {
            JSONObject json = new JSONObject();
            json.put("Guid",guid);
            gateway.sendMessage2Mqtt(json.toJSONString(),topic);
        }
    }

    private String updateDeviceAndBatteryStatusByEvent(String message) {
        try {
            JSONObject json = JSONObject.parseObject(message);
            String guid = json.getString("Guid");
            String time = json.getString("Time");
            int status = json.getIntValue("CurState");
            int switchReason = json.getIntValue("SwitchReason");
            int switchState = json.getIntValue("SwitchState");
            String batStatus = json.getString("cd");
            String cardId = guid.substring(17);
            String seconds = time.split("\\.")[0];
            Date date = new Date(Long.parseLong(seconds) * 1000 - (8 * 60 * 60 * 1000));
            Device dev = deviceService.getDeviceByCardId(cardId);
            if (Objects.isNull(dev)) {
                log.error("[ReportEventHandler-updateDeviceAndBatteryStatusByEvent]device {} is not exit in database.", cardId);
                return null;
            }
            // 根据设备状态添加告警信息
            if (alarmStatus.contains(switchReason)) {
                Alarm devAlarm = Alarm.builder().alarmObject(dev.getName()).deviceId(cardId).alarmType(1)
                    .alarmTime(date).build();
                devAlarm.setRemarks(getFailReason(switchReason));
                alarmService.save(devAlarm);
                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(devAlarm));
                jsonObject.put("alarmMethod", Collections.singletonList(1));
                // 发送给前端
                sender.send(RabbitConfig.DEFAULT_EXCHANGE, RabbitConfig.SYSTEM_ALARM, jsonObject.toJSONString());
                dev.setStatus(5);
            } else {
                dev.setStatus(switchState==0?2:switchState==1?3:switchState==2?4:0);
            }
            dev.setLastConnTime(date);
            deviceService.updateById(dev);
            List<Battery> list = batteryService.findList(Battery.builder().deviceId(dev.getId()).build());
            if (StringUtils.isNotEmpty(batStatus) && CollectionUtils.isNotEmpty(list)) {
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setStatus((int)batStatus.charAt(i));
                    batteryService.updateById(list.get(i));
                }
            }
            return guid;
        } catch (Exception e) {
            throw new NetServiceException(ResultEnum.PARAM_INVALIDATE.getCode(), e.getMessage());
        }
    }
    private String getFailReason(int type) {
        String reason;
        switch (type){
            case 1:
                reason = "停电发生";
                break;
            case 3:
                reason = "电池温度异常";
                break;
            case 5:
                reason = "环境温度异常";
                break;
            case 9:
                reason = "电池电压小于放电电压下限";
                break;
            case 10:
                reason = "电池单体电压超上限";
                break;
            case 12:
                reason = "电池充电无充电电流";
                break;
            case 13:
                reason = "电池充电超时";
                break;
            case 15:
                reason = "电池放电放电电流不达标";
                break;
            case 16:
                reason = "电池单体电压超下限";
                break;
            case 17:
                reason = "电池放电超时";
                break;
            case 18:
                reason = "电池放电超量";
                break;
            case 19:
                reason = "设备自身故障";
                break;
            case 21:
                reason = "通信模块信号弱";
                break;
            default:
                reason = "未知";
        }
        return reason;
    }
}
