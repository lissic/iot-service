package com.hlytec.cloud.biz.device.service;

import com.hlytec.cloud.biz.device.mapper.BatteryMapper;
import com.hlytec.cloud.biz.device.model.entity.Battery;
import com.hlytec.cloud.biz.device.model.entity.BatteryGroup;
import com.hlytec.cloud.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @description: BatteryService
 * @author: zero
 * @date: 2021/6/30 11:19
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class BatteryService extends BaseService<BatteryMapper, Battery> {

    @Autowired
    private BatteryGroupService groupService;

    @Transactional(readOnly = false)
    public String saveBattery(Battery battery) {
        String id = save(battery);
        BatteryGroup batteryGroup = groupService.get(battery.getBatteryGrpId());
        if (Objects.nonNull(battery.getVoltage())) {
            Double totalVol = batteryGroup.getTotalVol();
            if (Objects.nonNull(totalVol)) {
                totalVol+=battery.getVoltage();
            } else {
                totalVol = battery.getVoltage();
            }
            batteryGroup.setTotalVol(totalVol);
        }
        if (Objects.nonNull(battery.getCapacity())) {
            Double totalCapacity = batteryGroup.getTotalCapacity();
            if (Objects.nonNull(totalCapacity)) {
                totalCapacity+=battery.getCapacity();
            } else {
                totalCapacity = battery.getCapacity();
            }
            batteryGroup.setTotalCapacity(totalCapacity);
            batteryGroup.setRemainingCapacity(totalCapacity);
        }
        groupService.updateById(batteryGroup);
        return id;
    }
}
