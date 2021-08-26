package com.hlytec.cloud.biz.device.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hlytec.cloud.biz.device.model.entity.Device;
import com.hlytec.cloud.biz.report.model.po.DeviceAlarm;
import com.hlytec.cloud.biz.report.model.po.DeviceNumWithStationPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @description: DeviceMapper
 * @author: zero
 * @date: 2021/5/31 11:22
 */
@Mapper
public interface DeviceMapper extends BaseMapper<Device> {
    /**
     * 设备状态分类统计
     * @param loginName loginName
     * @param area area
     * @return List<Map<String, Integer>>
     */
    List<Map<String, Integer>> getNumWithStatus(String loginName, String area);

    /**
     * 设备站点分类统计
     * @param loginName loginName
     * @param area area
     * @return List<DeviceNumWithStationPo>
     */
    List<DeviceNumWithStationPo> getNumWithStation(String loginName, String area);

    /**
     * 统计系统四色告警
     *
     * @return List<DeviceNumWithAreaPo>
     */
    List<Map<String, Integer>> getNumWithSysAlarm();

    /**
     * 统计设备四色告警
     *
     * @return List<DeviceNumWithAreaPo>
     */
    List<Map<String, Integer>> getNumWithDevAlarm();

    /**
     * 根据板卡信息获取设备
     *
     * @param cardId cardId
     * @return Device
     */
    Device getDeviceByCardId(String cardId);

    /**
     * 查询设备告警top10
     * @param loginName loginName
     * @param area area
     * @return  List<DeviceAlarm>
     */
    List<DeviceAlarm> getAlarmByDevice(String loginName, String area);

    /**
     * 根据区域获取所有设备
     * @param area area
     * @return List<String>
     */
    List<String> getDeviceByArea(String area);
}
