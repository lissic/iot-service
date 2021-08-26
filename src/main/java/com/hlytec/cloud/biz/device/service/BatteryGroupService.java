package com.hlytec.cloud.biz.device.service;

import java.util.List;
import java.util.Objects;

import com.hlytec.cloud.biz.device.model.entity.Battery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hlytec.cloud.biz.device.mapper.BatteryGroupMapper;
import com.hlytec.cloud.biz.device.model.entity.BatteryGroup;
import com.hlytec.cloud.biz.device.model.vo.QueryBatteryGroupVo;
import com.hlytec.cloud.common.service.BaseService;

/**
 * @description: BatteryGroupService
 * @author: zero
 * @date: 2021/6/30 9:34
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class BatteryGroupService extends BaseService<BatteryGroupMapper, BatteryGroup> {
    @Autowired
    private BatteryGroupMapper groupMapper;
    @Autowired
    private BatteryService batteryService;

    public List<BatteryGroup> findBatteryGroupList(QueryBatteryGroupVo batteryGroup) {
        QueryWrapper<BatteryGroup> queryWrapper = getBatteryGroupQueryWrapper(batteryGroup);
        List<BatteryGroup> batteryGroups = groupMapper.selectList(queryWrapper);
        batteryGroups.forEach(bg -> {
            List<Battery> bats = batteryService.findList(Battery.builder().batteryGrpId(bg.getId()).build());
            bg.setBatteries(bats);
        });
        return batteryGroups;
    }

    private QueryWrapper<BatteryGroup> getBatteryGroupQueryWrapper(QueryBatteryGroupVo batteryGroup) {
        QueryWrapper<BatteryGroup> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(batteryGroup.getDeviceId())) {
            queryWrapper.like("device_id", batteryGroup.getDeviceId());
        }
        if (StringUtils.isNotEmpty(batteryGroup.getName())) {
            queryWrapper.like("name", batteryGroup.getName());
        }
        if (StringUtils.isNotEmpty(batteryGroup.getGroupId())) {
            queryWrapper.eq("group_id", batteryGroup.getGroupId());
        }
        if (StringUtils.isNotEmpty(batteryGroup.getBrand())) {
            queryWrapper.eq("brand", batteryGroup.getBrand());
        }
        if (StringUtils.isNotEmpty(batteryGroup.getManufacturer())) {
            queryWrapper.eq("manufacturer", batteryGroup.getManufacturer());
        }
        if (StringUtils.isNotEmpty(batteryGroup.getModel())) {
            queryWrapper.eq("model", batteryGroup.getModel());
        }
        if (StringUtils.isNotEmpty(batteryGroup.getBatchNum())) {
            queryWrapper.eq("batchNum", batteryGroup.getBatchNum());
        }
        if (Objects.nonNull(batteryGroup.getStatus())) {
            queryWrapper.eq("status", batteryGroup.getStatus());
        }
        if (Objects.nonNull(batteryGroup.getType())) {
            queryWrapper.eq("type", batteryGroup.getType());
        }
        if (Objects.nonNull(batteryGroup.getInstallTime())) {
            queryWrapper.eq("install_time", batteryGroup.getInstallTime());
        }
        queryWrapper.orderByDesc("create_time");
        return queryWrapper;
    }
}
