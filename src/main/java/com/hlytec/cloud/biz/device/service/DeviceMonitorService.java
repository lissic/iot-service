package com.hlytec.cloud.biz.device.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hlytec.cloud.biz.device.model.vo.QueryDeviceLogVo;
import com.hlytec.cloud.common.constants.SysConstants;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.hlytec.cloud.biz.device.mapper.DeviceMonitorMapper;
import com.hlytec.cloud.biz.device.model.entity.*;
import com.hlytec.cloud.biz.device.model.vo.QueryBatteryGroupVo;
import com.hlytec.cloud.common.exception.NetServiceException;
import com.hlytec.cloud.common.result.ResultEnum;
import com.hlytec.cloud.common.service.BaseService;
import com.hlytec.cloud.message.mqtt.config.MqttGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: DeviceMonitorService
 * @author: zero
 * @date: 2021/7/19 10:54
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, readOnly = false)
public class DeviceMonitorService extends BaseService<DeviceMonitorMapper, DeviceMonitor> {

    @Autowired
    private BatteryGroupService groupService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private BatteryService batteryService;

    @Autowired
    private DeviceLogService logService;

    @Autowired
    private MqttGateway gateway;

    public String saveOrUpdate(DeviceMonitor deviceMonitor) {
        String id = "";
        DeviceMonitor exits = get(DeviceMonitor.builder().deviceId(deviceMonitor.getDeviceId()).build());
        if (Objects.nonNull(exits)) {
            // 如果存在，则更新
            id = exits.getId();
            deviceMonitor.setId(id);
            updateById(deviceMonitor);
        } else {
            id = save(deviceMonitor);
        }
        return id;
    }

    /**
     * 获取设备监控总览信息
     * @param deviceId deviceId
     * @return DeviceMonitor
     */
    public DeviceMonitor survey(String deviceId) {
        Device device = deviceService.get(deviceId);
        QueryBatteryGroupVo query = new QueryBatteryGroupVo();
        query.setDeviceId(deviceId);
        List<BatteryGroup> batteryGroupList = groupService.findBatteryGroupList(query);
        DeviceMonitor monitor = get(DeviceMonitor.builder().deviceId(deviceId).cardId(device.getCardId()).build());
        if (Objects.nonNull(monitor)) {
            monitor.setStatus(device.getStatus());
            monitor.setBatteryGroups(batteryGroupList);
            monitor.setCardId(device.getCardId());
        }
        return monitor;
    }

    /**
     * 同步设备状态
     * @param cardId cardId
     */
    public void syncStatus(String cardId) {
        String topic ="/ToEqu/RYX/4_GetBatteryBasicInfo";
        if (StringUtils.isNotEmpty(cardId)) {
            JSONObject json = new JSONObject();
            json.put("Guid", SysConstants.GUID_PREFIX + cardId);
            gateway.sendMessage2Mqtt(json.toJSONString(), topic);
            Device device = deviceService.getDeviceByCardId(cardId);
            logService.saveOrUpdate(DeviceLog.buildDevLog(device,json.toJSONString(), 4));
        } else {
            throw new NetServiceException(ResultEnum.PARAM_INVALIDATE);
        }
    }

    /**
     * 根据板卡ID获取设备监控信息
     * @param cardId cardId
     * @return DeviceMonitor
     */
    public DeviceMonitor getDeviceMonitorByCardId(String cardId) {
        Device dev = deviceService.getDeviceByCardId(cardId);
        if (Objects.nonNull(dev)) {
            return survey(dev.getId());
        }
        return null;
    }

    /**
     * 更新设备信息
     * @param message message
     * @return guid
     */
    public String updateDeviceByMsg(String message) {
        try {
            JSONObject json = JSONObject.parseObject(message);
            String guid = json.getString("Guid");
            String time = json.getString("Time");
            Double totalVol = json.getDouble("BatteryVoltage");
            Double rectifierVol = json.getDouble("RectifierOutputVoltage");
            Double envTemp = json.getDouble("AmbientTemperature");
            Double envHumidity = json.getDouble("AmbientHumidity");
            List<Double> batteryCellsVol = json.getJSONArray("BatteryCellsVoltage").toJavaList(Double.class);
            int status = json.getIntValue("BatteryState");
            int signal = json.getIntValue("SignalIntensity");

            String cardId = guid.substring(17);
            String seconds = time.split("\\.")[0];
            // 设置设备相关参数
            Device dev = deviceService.getDeviceByCardId(cardId);
            if (Objects.isNull(dev)) {
                log.error("[DeviceMonitorService-updateDeviceByMsg]device {} is not exit in database.", cardId);
                return null;
            }
            dev.setStatus(status==0?2:status==1?3:status==2?4:0);
            dev.setLastConnTime(new Date(Long.parseLong(seconds) * 1000));
            deviceService.updateById(dev);
            updateMonitor(totalVol, rectifierVol, envTemp, envHumidity, signal, dev);
            // 设置每个电池的电压
            if(CollectionUtils.isNotEmpty(batteryCellsVol)) {
                List<Battery> list = batteryService.findList(Battery.builder().deviceId(dev.getId()).build());
                for (int i = 0; i < list.size(); i++) {
                    Battery battery = list.get(i);
                    battery.setVoltage(batteryCellsVol.get(i));
                    batteryService.updateById(battery);
                }
            }
            // 设置电池组状态
            List<BatteryGroup> batteryGroup = groupService.findList(BatteryGroup.builder().deviceId(dev.getId()).build());
            batteryGroup.forEach(group -> {
                if (dev.getStatus() == 0 || dev.getStatus() == 5) {
                    group.setStatus(1);
                } else {
                    group.setStatus(0);
                }
                group.setTotalVol(totalVol);
                groupService.updateById(group);
            });
            return guid;
        } catch (Exception e) {
            throw new NetServiceException(ResultEnum.UPDATE_DEVICE_FAILED.getCode(), e.getMessage());
        }
    }

    private void updateMonitor(Double totalVol, Double rectifierVol, Double envTemp, Double envHumidity, int signal,
        Device dev) {
        List<BatteryGroup> groups = groupService.findList(BatteryGroup.builder().deviceId(dev.getId()).build());
        String groupIds = groups.stream().map(BatteryGroup::getId).collect(Collectors.joining(","));
        DeviceMonitor monitor =
            DeviceMonitor.builder().deviceId(dev.getId()).totalVol(totalVol).rectifierVol(rectifierVol).envTemp(envTemp)
                .envHumidity(envHumidity).signalDbm(signal).batteryGrpId(groupIds).build();
        saveOrUpdate(monitor);
    }

    /**
     * 获取设备操作日志
     * @param deviceLogVo deviceId
     * @return List<DeviceLog>
     */
    public List<DeviceLog> getRunLog(QueryDeviceLogVo deviceLogVo) {
        QueryWrapper<DeviceLog> query = new QueryWrapper<>();
        if (StringUtils.isEmpty(deviceLogVo.getDeviceId())) {
            throw new NetServiceException(ResultEnum.PARAM_INVALIDATE);
        }
        if (Objects.nonNull(deviceLogVo.getStartTime()) && Objects.nonNull(deviceLogVo.getEndTime())) {
            query.between("create_time", deviceLogVo.getStartTime(), deviceLogVo.getEndTime());
        }
        query.eq("device_id", deviceLogVo.getDeviceId());
        query.orderByDesc("create_time");
        return logService.queryRunLog(query);
    }

    /**
     * TODO 启动充电
     * @param deviceId deviceId
     */
    public void startCharge(String deviceId) {

    }

    /**
     * 启动放电
     * @param deviceId deviceId
     */
    public void startDischarge(String deviceId) {
        String topic ="/ToEqu/RYX/b_StartBatteryConfirmCapacity";
        Device dev = deviceService.get(deviceId);
        // 已经出于放电状态
        if (dev.getStatus() == 4) {
            throw new NetServiceException(ResultEnum.DEVICE_HAS_DISCHARGE);
        } else if (dev.getStatus() == 1 ||  dev.getStatus() == 2) {
            // 设备在线或休眠
            String cardId = dev.getCardId();
            if (StringUtils.isNotEmpty(cardId)) {
                JSONObject json = new JSONObject();
                json.put("Guid", SysConstants.GUID_PREFIX + cardId);
                gateway.sendMessage2Mqtt(json.toJSONString(), topic);
                logService.saveOrUpdate(DeviceLog.buildDevLog(dev, json.toJSONString(), 11));
            } else {
                log.error("[startDischarge]device {} cardId is empty!", dev.getName());
            }
        } else {
            // 设备离线或故障
            throw new NetServiceException(ResultEnum.DEVICE_OFFLINE);
        }
    }

}
