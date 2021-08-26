package com.hlytec.cloud.biz.report.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.hlytec.cloud.biz.report.model.po.DeviceAlarm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hlytec.cloud.biz.device.service.DeviceService;
import com.hlytec.cloud.biz.report.model.po.DeviceNumWithStationPo;
import com.hlytec.cloud.common.controller.BaseController;
import com.hlytec.cloud.common.result.CommonResult;
import com.hlytec.cloud.common.result.ResultEnum;

/**
 * @description: DeviceOverviewController
 * @author: zero
 * @date: 2021/7/8 9:14
 */
@RestController
@RequestMapping("/report/device/statistics")
public class DeviceOverviewController extends BaseController {

    @Autowired
    private DeviceService deviceService;

    /**
     * 根据状态分类统计
     * @return CommonResult<Object>
     */
    @GetMapping("/withStatus")
    public CommonResult<Object> withStatus() {
        List<Map<String, Integer>> numWithStatus = deviceService.getNumWithStatus();
        return success(numWithStatus);
    }

    /**
     * 根据站点分类统计
     * @return CommonResult<Object>
     */
    @GetMapping("/withStation")
    public CommonResult<Object> withStation() {
        List<DeviceNumWithStationPo> numWithStatus = deviceService.getNumWithStation();
        return success(numWithStatus);
    }

    /**
     * 告警分类统计
     * @return CommonResult<Object>
     */
    @GetMapping("/withAlarm")
    public CommonResult<Object> withAlarm(Integer alarmType) {
        if (Objects.isNull(alarmType)) {
            return fail(ResultEnum.PARAM_INVALIDATE);
        }
        List<Map<String, Integer>> numWithAlarm = deviceService.getNumWithAlarm(alarmType);
        return success(numWithAlarm);
    }

    /**
     * 根据设备统计告警top10
     * @return CommonResult<Object>
     */
    @GetMapping("/withDeviceAlarm")
    public CommonResult<Object> alarmGroupByDevice() {
        List<DeviceAlarm> alarmByDevice = deviceService.getAlarmByDevice();
        return success(alarmByDevice);
    }

    /**
     * 根据时间段统计告警消息
     * @return CommonResult<Object>
     */
    @GetMapping("/withAlarmTime")
    public CommonResult<Object> alarmMsgByTime(Integer alarmType) {
        if (Objects.isNull(alarmType)) {
            return fail(ResultEnum.PARAM_INVALIDATE);
        }
        Map<String, Long> result = deviceService.alarmMsgByTime(alarmType);
        return success(result);
    }
}
