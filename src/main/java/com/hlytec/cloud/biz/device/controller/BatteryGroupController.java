package com.hlytec.cloud.biz.device.controller;

import com.hlytec.cloud.biz.device.model.vo.QueryBatteryGroupVo;
import com.hlytec.cloud.common.entity.CommonParamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hlytec.cloud.biz.device.model.entity.BatteryGroup;
import com.hlytec.cloud.biz.device.service.BatteryGroupService;
import com.hlytec.cloud.common.controller.BaseController;
import com.hlytec.cloud.common.result.CommonResult;

import java.util.List;

/**
 * @description: BatteryController
 * @author: zero
 * @date: 2021/6/9 14:37
 */
@RestController
@RequestMapping("/net/batteryGroup")
public class BatteryGroupController extends BaseController {
    @Autowired
    private BatteryGroupService batteryGroupService;

    @PostMapping("/save")
    public CommonResult<Object> save(@RequestBody BatteryGroup batteryGroup) {
        String id = batteryGroupService.save(batteryGroup);
        return success(id);
    }

    @DeleteMapping("/delete")
    public CommonResult<Object> delete(@RequestBody CommonParamVo deleteByIdsVo) {
        batteryGroupService.batchDelete(deleteByIdsVo.getIds());
        return success();
    }

    @PutMapping("/update")
    public CommonResult<Object> update(@RequestBody BatteryGroup batteryGroup) {
        batteryGroupService.updateById(batteryGroup);
        return success();
    }

    @PostMapping("/list")
    public CommonResult<Object> findList(@RequestBody QueryBatteryGroupVo batteryGroup) {
        List<BatteryGroup> batteryGroupList = batteryGroupService.findBatteryGroupList(batteryGroup);
        return success(batteryGroupList);
    }

    @GetMapping("/{id}")
    public CommonResult<Object> getBatteryGroup(@PathVariable("id") String id) {
        BatteryGroup batteryGroup = batteryGroupService.get(id);
        return success(batteryGroup);
    }

}
