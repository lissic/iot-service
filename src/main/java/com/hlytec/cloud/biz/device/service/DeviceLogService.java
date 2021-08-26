package com.hlytec.cloud.biz.device.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hlytec.cloud.biz.device.mapper.DeviceLogMapper;
import com.hlytec.cloud.biz.device.model.entity.DeviceLog;
import com.hlytec.cloud.common.service.BaseService;

/**
 * @description: DeviceLogService
 * @author: zero
 * @date: 2021/7/21 14:31
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class DeviceLogService extends BaseService<DeviceLogMapper, DeviceLog> {

    @Autowired
    private DeviceLogMapper logMapper;

    @Transactional(readOnly = false)
    public void saveOrUpdate(DeviceLog log) {
        DeviceLog deviceLog = findOneWithResultNull(log);
        if (Objects.nonNull(deviceLog)) {
            deviceLog.setUpdateTime(null);
            updateById(deviceLog);
        } else {
            save(log);
        }
    }

    public List<DeviceLog> queryRunLog(QueryWrapper<DeviceLog> query) {
        List<DeviceLog> deviceLogs = logMapper.selectList(query);
        if(CollectionUtils.isNotEmpty(deviceLogs)) {
            return deviceLogs;
        }
        return Collections.emptyList();
    }

    public DeviceLog findOneWithResultNull(DeviceLog log) {
        QueryWrapper<DeviceLog> wrapper = new QueryWrapper<>();
        wrapper.eq("result", 2);
        wrapper.eq("operation_type", log.getOperationType());
        if (StringUtils.isNotEmpty(log.getDeviceId())) {
            wrapper.eq("device_id", log.getDeviceId());
        }
        if (StringUtils.isNotEmpty(log.getCardId())) {
            wrapper.eq("card_id", log.getCardId());
        }
        if (StringUtils.isNotEmpty(log.getOperationPerson())) {
            wrapper.eq("operation_person",log.getOperationPerson());
        }
        return logMapper.selectOne(wrapper);
    }

    public List<DeviceLog> findListWithResultNull() {
        QueryWrapper<DeviceLog> wrapper = new QueryWrapper<>();
        wrapper.eq("result", 2);
        return logMapper.selectList(wrapper);
    }

}
