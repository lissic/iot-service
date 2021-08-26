package com.hlytec.cloud.biz.device.mapper;

import com.hlytec.cloud.biz.device.model.po.DeviceConfigPo;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hlytec.cloud.biz.device.model.entity.DeviceConfig;

import java.util.List;

/**
 * @description: DeviceMapper
 * @author: zero
 * @date: 2021/5/31 11:22
 */
@Mapper
public interface DeviceConfigMapper extends BaseMapper<DeviceConfig> {

    /**
     * 获取设备所有配置项
     * @param deviceId deviceId
     * @return List<DeviceConfig>
     */
    List<DeviceConfigPo> getConfig(String deviceId);

}
