package com.hlytec.cloud.biz.device.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hlytec.cloud.biz.device.mapper.DeviceSoftMapper;
import com.hlytec.cloud.biz.device.mapper.DeviceUpgradeRecordMapper;
import com.hlytec.cloud.biz.device.model.entity.DeviceSoft;
import com.hlytec.cloud.biz.device.model.entity.DeviceUpgradeRecord;
import com.hlytec.cloud.biz.device.model.vo.DeviceRecordVo;
import com.hlytec.cloud.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zero
 * @description DeviceSoftService
 * @date 2021/7/29 15:11
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class DeviceSoftService extends BaseService<DeviceSoftMapper, DeviceSoft> {

    @Autowired
    private DeviceUpgradeRecordMapper upgradeRecordMapper;

    @Transactional(readOnly = false)
    public void saveUpgradeRecord(DeviceRecordVo upgradeRecord) {
        upgradeRecord.getDevices().forEach(dev -> {
            DeviceUpgradeRecord record = DeviceUpgradeRecord.builder().deviceId(dev.getId()).deviceName(dev.getName())
                .softId(upgradeRecord.getSoftId()).build();
            upgradeRecordMapper.insert(record);
        });
    }

    @Transactional(readOnly = false)
    public void startUpgrade(List<String> ids) {
        ids.forEach(id -> {
            DeviceUpgradeRecord deviceUpgradeRecord = upgradeRecordMapper.selectById(id);
            deviceUpgradeRecord.setStatus(1);
            // TODO 通知设备进行升级
            upgradeRecordMapper.updateById(deviceUpgradeRecord);
        });
    }

    public List<DeviceUpgradeRecord> findRecordPage(String softId) {
        QueryWrapper<DeviceUpgradeRecord> query = new QueryWrapper<>();
        query.eq("soft_id", softId);
        query.orderByDesc("create_time");
        List<DeviceUpgradeRecord> deviceUpgradeRecords = upgradeRecordMapper.selectList(query);
        return deviceUpgradeRecords;
    }

}
