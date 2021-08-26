package com.hlytec.cloud.biz.device.service;

import com.hlytec.cloud.biz.device.model.entity.Device;
import com.hlytec.cloud.biz.device.model.entity.DeviceLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.hlytec.cloud.biz.device.mapper.DeviceConfigMapper;
import com.hlytec.cloud.biz.device.model.entity.DeviceConfig;
import com.hlytec.cloud.common.constants.SysConstants;
import com.hlytec.cloud.common.service.BaseService;
import com.hlytec.cloud.message.mqtt.config.MqttGateway;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: DeviceService
 * @author: zero
 * @date: 2021/5/31 11:21
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class DeviceConfigService extends BaseService<DeviceConfigMapper, DeviceConfig> {
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceLogService logService;

    @Autowired
    private MqttGateway gateway;

    /**
     * 根据配置类型下发配置到设备
     * @param deviceConfig deviceConfig
     */
    @Transactional(readOnly = false)
    public void uploadConfig(DeviceConfig deviceConfig) {
        Integer configType = deviceConfig.getConfigType();
        String cardId = deviceConfig.getCardId();
        String deviceId = deviceConfig.getDeviceId();
        JSONObject configContent = deviceConfig.getConfigContent();
        String guid = SysConstants.GUID_PREFIX + cardId;
        configContent.put("Guid",guid);
        String topic = getTopic(configType);
        if (StringUtils.isNotEmpty(topic)) {
            gateway.sendMessage2Mqtt(configContent.toJSONString(), topic);
            // 添加设备操作日志
            Device device = deviceService.get(deviceId);
            logService.saveOrUpdate(DeviceLog.buildDevLog(device, configContent.toJSONString(), getOpType(configType)));
        } else {
            log.error("upload config failed, cause is topic is invalidate.");
        }

    }

    private Integer getOpType(Integer configType) {
        int type;
        switch (configType) {
            case 1:
                type = 8;
                break;
            case 2:
                type = 10;
                break;
            case 3:
                type = 9;
                break;
            case 5:
                type = 7;
                break;
            case 99:
                type = 6;
                break;
            default:
                type = 1;
                break;
        }
        return type;
    }

    private String getTopic(Integer configType) {
        String topic;
        switch (configType){
            case 0:
                topic = "/ToEqu/RYX/1_SetAppIpPort";
                break;
            case 1:
                topic = "/ToEqu/RYX/8_SetBatteryChargingParam";
                break;
            case 2:
                topic = "/ToEqu/RYX/a_SetBatteryConfirmCapacityParam";
                break;
            case 3:
                topic = "/ToEqu/RYX/9_SetBatteryTimerParam";
                break;
            case 5:
                topic = "/ToEqu/RYX/7_SetBatteryTemperatureParam";
                break;
            case 99:
                topic = "/ToEqu/RYX/6_SetBatteryFieldParam";
                break;
        default:
            topic = "";
            break;
        }
        return topic;
    }

    /**
     * 获取设备所有配置信息
     * @param cardId cardId
     */
    @Transactional(readOnly = false)
    public void downloadConfig(String cardId) {
        String topic = "/ToEqu/RYX/5_GetBatteryCfgInfo";
        if (StringUtils.isNotEmpty(cardId)) {
            JSONObject json = new JSONObject();
            json.put("Guid", SysConstants.GUID_PREFIX + cardId);
            gateway.sendMessage2Mqtt(json.toJSONString(), topic);
            // 添加设备操作日志
            Device device = deviceService.getDeviceByCardId(cardId);
            logService.saveOrUpdate(DeviceLog.buildDevLog(device, json.toJSONString(), 5));
        }
    }
}
