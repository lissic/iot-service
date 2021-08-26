package com.hlytec.cloud.biz.device.controller;

import com.hlytec.cloud.biz.device.model.entity.DeviceLog;
import com.hlytec.cloud.biz.device.model.vo.QueryDeviceLogVo;
import com.hlytec.cloud.biz.system.log.annotation.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hlytec.cloud.biz.device.model.entity.DeviceMonitor;
import com.hlytec.cloud.biz.device.service.DeviceMonitorService;
import com.hlytec.cloud.common.controller.BaseController;
import com.hlytec.cloud.common.result.CommonResult;

import java.util.List;

/**
 * @description: DeviceMonitorController
 * @author: zero
 * @date: 2021/6/9 14:16
 */
@RestController
@RequestMapping("/net/device/monitor")
public class DeviceMonitorController extends BaseController {

    @Autowired
    private DeviceMonitorService monitorService;

    /**
     * 设备监控
     * @param deviceId deviceId
     * @return CommonResult
     */
    @GetMapping("/{deviceId}")
    public CommonResult<Object> survey(@PathVariable("deviceId") String deviceId) {
        DeviceMonitor survey = monitorService.survey(deviceId);
        return success(survey);
    }

    /**
     * 同步设备状态
     * @param cardId cardId
     * @return CommonResult
     */
    @Log(module = "设备管理", description = "同步设备状态")
    @GetMapping("/syncStatus")
    public CommonResult<Object> syncStatus(String cardId) {
        monitorService.syncStatus(cardId);
        return success();
    }

    /**
     * 获取设备运行日志
     * @param deviceLogVo deviceLogVo
     * @return CommonResult<Object>
     */
    @Log(module = "设备管理", description = "获取操作日志")
    @PostMapping("/runLog")
    public CommonResult<Object> getRunLog(@RequestBody QueryDeviceLogVo deviceLogVo) {
        List<DeviceLog> runLog = monitorService.getRunLog(deviceLogVo);
        return success(runLog);
    }

    /**
     * 启动充电
     * @param deviceId deviceId
     * @return CommonResult<Object>
     */
    @Log(module = "设备管理", description = "启动充电")
    @PutMapping("/startCharge/{deviceId}")
    public CommonResult<Object> startCharge(@PathVariable("deviceId") String deviceId) {
        monitorService.startCharge(deviceId);
        return success();
    }

    /**
     * 启动放电
     * @param deviceId deviceId
     * @return CommonResult<Object>
     */
    @Log(module = "设备管理", description = "启动放电")
    @PutMapping("/startDischarge/{deviceId}")
    public CommonResult<Object> startDischarge(@PathVariable("deviceId") String deviceId) {
        monitorService.startDischarge(deviceId);
        return success();
    }

}
