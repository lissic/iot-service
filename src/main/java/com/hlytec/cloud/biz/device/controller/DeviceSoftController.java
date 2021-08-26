package com.hlytec.cloud.biz.device.controller;

import afu.org.checkerframework.checker.oigj.qual.O;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlytec.cloud.biz.device.model.entity.DeviceSoft;
import com.hlytec.cloud.biz.device.model.entity.DeviceUpgradeRecord;
import com.hlytec.cloud.biz.device.model.vo.DeviceRecordVo;
import com.hlytec.cloud.biz.device.model.vo.QueryDeviceSoftVo;
import com.hlytec.cloud.biz.device.service.DeviceSoftService;
import com.hlytec.cloud.common.controller.BaseController;
import com.hlytec.cloud.common.entity.CommonParamVo;
import com.hlytec.cloud.common.result.CommonResult;
import com.hlytec.cloud.common.result.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author zero
 * @description DeviceSoftController
 * @date 2021/7/30 14:18
 */
@RestController
@RequestMapping("/net/device/soft")
public class DeviceSoftController extends BaseController {
    @Autowired
    private DeviceSoftService softService;

    @PostMapping("/save")
    public CommonResult<Object> saveSoft(@RequestBody @Validated DeviceSoft deviceSoft) {
        String softId = softService.save(deviceSoft);
        return success(softId);
    }

    @DeleteMapping("/delete")
    public CommonResult<Object> delSoft(@RequestBody CommonParamVo paramVo) {
        softService.batchDelete(paramVo.getIds());
        return success();
    }

    @PutMapping("/update")
    public CommonResult<Object> updateSoft(@RequestBody @Validated DeviceSoft deviceSoft) {
        softService.updateById(deviceSoft);
        return success();
    }

    @PostMapping("/page")
    public CommonResult<Object> page(@RequestBody QueryDeviceSoftVo queryDeviceSoftVo) {
        Page<DeviceSoft> page = new Page<>(queryDeviceSoftVo.getPageNo(), queryDeviceSoftVo.getPageSize());
        QueryWrapper<DeviceSoft> param = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(queryDeviceSoftVo.getName())) {
            param.like("name",queryDeviceSoftVo.getName());
        }
        if (Objects.nonNull(queryDeviceSoftVo.getUpgradeType())) {
            param.eq("utype",queryDeviceSoftVo.getUpgradeType());
        }
        param.orderByDesc("create_time");
        PageResult<DeviceSoft> result = softService.findPageWithParam(page, param);
        return success(result);
    }

    @PostMapping("/saveRecord")
    public CommonResult<Object> saveUpgradeRecord(@RequestBody DeviceRecordVo upgradeRecord) {
        softService.saveUpgradeRecord(upgradeRecord);
        return success();
    }

    @PostMapping("/startUpgrade")
    public CommonResult<Object> startUpgrade(@RequestBody CommonParamVo paramVo) {
        softService.startUpgrade(paramVo.getIds());
        return success();
    }

    @GetMapping("/recordPage/{softId}")
    public CommonResult<Object> findRecordPage(@PathVariable("softId") String softId) {
        List<DeviceUpgradeRecord> recordPage = softService.findRecordPage(softId);
        return success(recordPage);
    }
}
