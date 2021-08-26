package com.hlytec.cloud.biz.device.timing;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.hlytec.cloud.biz.device.model.entity.Battery;
import com.hlytec.cloud.biz.device.model.entity.BatteryGroup;
import com.hlytec.cloud.biz.device.model.entity.Device;
import com.hlytec.cloud.biz.device.model.entity.DeviceLog;
import com.hlytec.cloud.biz.device.service.BatteryGroupService;
import com.hlytec.cloud.biz.device.service.BatteryService;
import com.hlytec.cloud.biz.device.service.DeviceLogService;
import com.hlytec.cloud.biz.device.service.DeviceService;
import com.hlytec.cloud.common.constants.SysConstants;
import com.hlytec.cloud.message.mqtt.config.MqttGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: 设备相关定时任务，如登录心跳
 * @author: zero
 * @date: 2021/7/13 16:43
 */
@Slf4j
@Component
@EnableScheduling
@EnableAsync
public class DeviceTiming {

    private final StringBuilder GUID_PREFIX = new StringBuilder(SysConstants.GUID_PREFIX);

    private final String topic = "/ToEqu/RYX/0_Login";

    private final long timeDiff = 1000 * 60 * 5;

    @Autowired
    private MqttGateway gateway;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private BatteryGroupService groupService;

    @Autowired
    private BatteryService batteryService;

    @Autowired
    private DeviceLogService deviceLogService;

    /**
     * 设备登录
     */
    @Async
    @Scheduled(fixedDelay = 60 * 1000)
    public void loginAndHeartbeats() {
        // 查询所有设备
        List<Device> devs = deviceService.findList(null);
        devs.forEach(device -> {
            String guid = GUID_PREFIX.append(device.getCardId()).toString();
            JSONObject json = new JSONObject();
            json.put("Guid",guid);
            gateway.sendMessage2Mqtt(json.toJSONString(),topic);
            GUID_PREFIX.delete(17,GUID_PREFIX.length());
        });

    }

    /**
     * 三分钟扫描一次所有设备，如果更新时间与当前时间差大于5分钟，则将状态置为离线
     */
    @Async
    @Scheduled(fixedDelay = 3 * 60 * 1000)
    public void loginTimeout() {
        long now = System.currentTimeMillis();
        // 查询所有设备
        List<Device> devs = deviceService.findList(null);
        devs.forEach(device -> {
            Date lastConnTime = device.getLastConnTime();
            long time = Objects.isNull(lastConnTime) ? 0 : lastConnTime.getTime();
            if ((now-time)>timeDiff) {
                // 更新电池状态
                List<Battery> batterys = batteryService.findList(Battery.builder().deviceId(device.getId()).build());
                batterys.forEach(battery -> {
                    battery.setStatus(1);
                    batteryService.updateById(battery);
                });
                // 更新电池组状态
                List<BatteryGroup> batteryGroup = groupService.findList(BatteryGroup.builder().deviceId(device.getId()).build());
                batteryGroup.forEach(group -> {
                    group.setStatus(1);
                    groupService.updateById(group);
                });
                device.setStatus(0);
                // 更新设备状态
                deviceService.updateById(device);
            }
        });
    }

    /**
     * 2分钟定时更新设备操作记录，下发操作如果超过两分钟则操作置为失败
     */
    @Async
    @Scheduled(fixedDelay = 60 * 1000)
    public void updateOperationLog() {
        long now = System.currentTimeMillis();
        // 查询所有设备
        List<DeviceLog> deviceLogList = deviceLogService.findListWithResultNull();
        deviceLogList.forEach(log -> {
            long time = log.getUpdateTime().getTime();
            if ((now-time)>(60*1000)) {
                log.setResult(0);
                deviceLogService.updateById(log);
            }
        });

    }
}
