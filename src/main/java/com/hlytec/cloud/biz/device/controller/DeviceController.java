package com.hlytec.cloud.biz.device.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.simpleframework.xml.core.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hlytec.cloud.biz.device.model.entity.Device;
import com.hlytec.cloud.biz.device.model.entity.DeviceConfig;
import com.hlytec.cloud.biz.device.model.vo.DeviceConfigVo;
import com.hlytec.cloud.biz.device.model.vo.QueryDeviceVo;
import com.hlytec.cloud.biz.device.service.DeviceConfigService;
import com.hlytec.cloud.biz.device.service.DeviceService;
import com.hlytec.cloud.biz.system.log.annotation.Log;
import com.hlytec.cloud.common.controller.BaseController;
import com.hlytec.cloud.common.entity.CommonParamVo;
import com.hlytec.cloud.common.exception.NetServiceException;
import com.hlytec.cloud.common.result.CommonResult;
import com.hlytec.cloud.common.result.PageResult;
import com.hlytec.cloud.common.result.ResultEnum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description: DeviceController
 * @author: zero
 * @date: 2021/5/31 11:24
 */
@Slf4j
@RestController
@RequestMapping("/net/device")
public class DeviceController extends BaseController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceConfigService configService;

    @Log(module = "设备管理", description = "新增")
    @PostMapping("/save")
    public CommonResult<Object> saveDevice(@RequestBody @Validate Device device) {
        String devId = deviceService.saveDevice(device);
        return success(devId);
    }

    @Log(module = "设备管理", description = "删除")
    @DeleteMapping("/delete")
    public CommonResult<Object> deleteDevice(@RequestBody CommonParamVo deleteDeviceVo) {
        deviceService.deleteDevice(deleteDeviceVo);
        return success();
    }

    @Log(module = "设备管理", description = "修改")
    @PutMapping("/update")
    public CommonResult<Object> updateDevice(@RequestBody @Validate Device device) {
        deviceService.updateById(device);
        return success();
    }

    @PostMapping("/list")
    public CommonResult<Object> findList(@RequestBody QueryDeviceVo queryDeviceVo, HttpServletRequest request) {
        String token = request.getHeader("token");
        List<Device> list = deviceService.findDeviceList(queryDeviceVo, token);
        return success(list);
    }

    @PostMapping("/page")
    public CommonResult<Object> findPage(@RequestBody QueryDeviceVo queryDeviceVo, HttpServletRequest request) {
        String token = request.getHeader("token");
        PageResult<Device> result = deviceService.findDevicePage(queryDeviceVo, token);
        return success(result);
    }

    @GetMapping("/{deviceId}")
    public CommonResult<Object> getDeviceDetail(@PathVariable("deviceId") String deviceId) {
        Device device = deviceService.getDevice(deviceId);
        return success(device);
    }

    @Log(module = "设备管理", description = "保存配置")
    @PostMapping("/config/save")
    public CommonResult<Object> config(@RequestBody DeviceConfigVo deviceConfigVo) {
        List<DeviceConfig> configs = deviceConfigVo.getConfigList();
        configs.forEach(config -> {
            DeviceConfig param =
                DeviceConfig.builder().deviceId(config.getDeviceId()).configType(config.getConfigType()).build();
            DeviceConfig deviceConfig = configService.get(param);
            if (Objects.isNull(deviceConfig)) {
                configService.save(config);
            } else {
                deviceConfig.setConfigContent(config.getConfigContent());
                configService.updateById(deviceConfig);
            }
        });
        return success();
    }

    @Log(module = "设备管理", description = "更新配置")
    @PutMapping("/config/update")
    public CommonResult<Object> updateConfig(@RequestBody DeviceConfigVo deviceConfigVo) {
        List<DeviceConfig> configs = deviceConfigVo.getConfigList();
        configs.forEach(config -> configService.updateById(config));
        return success();
    }

    @Log(module = "设备管理", description = "下发配置")
    @PostMapping("/config/upload")
    public CommonResult<Object> uploadConfig(@RequestBody DeviceConfig deviceConfig) {
        configService.uploadConfig(deviceConfig);
        return success();
    }

    @Log(module = "设备管理", description = "获取配置")
    @GetMapping("/config/download")
    public CommonResult<Object> downloadConfig(String cardId) {
        configService.downloadConfig(cardId);
        return success();
    }

    @GetMapping("/downloadExcel")
    public void downloadExcel(HttpServletResponse response) {
        try {
            deviceService.downloadExcel(response);
        } catch (IOException e) {
            log.error("downloadExcel error: {}", e.getMessage());
            throw new NetServiceException(ResultEnum.DOWNLOAD_EXCEL_FAILED.getCode(),
                ResultEnum.DOWNLOAD_EXCEL_FAILED.getMsg());
        }
    }

    @Log(module = "设备管理", description = "批量导入")
    @PostMapping("/batchSave")
    public CommonResult<Object> batchSave(@RequestParam("file") MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            deviceService.batchSave(inputStream);
        } catch (IOException | NetServiceException e) {
            log.error("read excel error: {}", e.getMessage());
            return fail(ResultEnum.BATCH_SAVE_FAILED);
        }
        return success();
    }

}
