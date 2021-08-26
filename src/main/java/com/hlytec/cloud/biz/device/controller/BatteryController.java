package com.hlytec.cloud.biz.device.controller;

import java.util.List;

import com.hlytec.cloud.common.entity.CommonParamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.hlytec.cloud.biz.device.model.entity.Battery;
import com.hlytec.cloud.biz.device.service.BatteryService;
import com.hlytec.cloud.common.controller.BaseController;
import com.hlytec.cloud.common.result.CommonResult;

/**
 * @description: BatteryController
 * @author: zero
 * @date: 2021/6/30 9:55
 */
@RestController
@RequestMapping("/net/battery")
public class BatteryController extends BaseController {
    @Autowired
    private BatteryService batteryService;

    @PostMapping("/save")
    public CommonResult<Object> save(@RequestBody @Validated Battery battery) {
        String id = batteryService.saveBattery(battery);
        return success(id);
    }

    @DeleteMapping("/delete")
    public CommonResult<Object> delete(@RequestBody CommonParamVo deleteByIdsVo) {
        batteryService.batchDelete(deleteByIdsVo.getIds());
        return success();
    }

    @PutMapping("/update")
    public CommonResult<Object> update(@RequestBody Battery battery) {
        batteryService.updateById(battery);
        return success();
    }

    @GetMapping("/list")
    public CommonResult<Object> findList(String batteryGroupId) {
        Battery build = Battery.builder().batteryGrpId(batteryGroupId).build();
        List<Battery> batteryList = batteryService.findList(build);
        return success(batteryList);
    }

    @GetMapping("/{id}")
    public CommonResult<Object> getBatteryGroup(@PathVariable("id") String id) {
        Battery battery = batteryService.get(id);
        return success(battery);
    }
}
