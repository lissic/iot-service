package com.hlytec.cloud.biz.system.station.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlytec.cloud.biz.device.model.entity.Device;
import com.hlytec.cloud.biz.device.service.DeviceService;
import com.hlytec.cloud.biz.system.station.model.entity.Station;
import com.hlytec.cloud.common.entity.CommonParamVo;
import com.hlytec.cloud.common.result.CommonResult;
import com.hlytec.cloud.common.result.PageResult;
import com.hlytec.cloud.common.result.ResultEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.hlytec.cloud.biz.system.station.service.StationService;
import com.hlytec.cloud.common.controller.BaseController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @description: StationController
 * @author: JackChen
 * @date: 2021/5/20 17:04
 */
@RestController
@RequestMapping("/sys/station")
public class StationController extends BaseController {
    @Autowired
    private StationService stationService;
    @Autowired
    private DeviceService deviceService;

    @PostMapping("/save")
    public CommonResult<Object> save(@RequestBody @Validated Station station) {
        String id = stationService.save(station);
        return success(id);
    }

    @DeleteMapping("/delete")
    public CommonResult<Object> delete(@RequestBody CommonParamVo commonParamVo) {
        List<String> ids = commonParamVo.getIds();
        List<String> cannotDel = new ArrayList<>();
        ids.forEach(id -> {
            Device device = deviceService.get(Device.builder().stationId(id).build());
            if (Objects.nonNull(device)) {
                cannotDel.add(id);
            }
        });
        if (CollectionUtils.isNotEmpty(cannotDel)) {
            return fail(ResultEnum.STATION_CANNOT_DEL);
        }
        stationService.batchDelete(ids);
        return success();
    }

    @PutMapping("/update")
    public CommonResult<Object> update(@RequestBody @Validated Station station) {
        stationService.updateById(station);
        return success();
    }

    @PostMapping("/page")
    public CommonResult<Object> page(@RequestBody Station station) {
        PageResult<Station> page = stationService.findPage(new Page<>(), station);
        return success(page);
    }

    @PostMapping("/list")
    public CommonResult<Object> list(@RequestBody Station station) {
        List<Station> list = stationService.findList(station);
        return success(list);
    }

    @GetMapping("/tree")
    public CommonResult<Object> tree(String area) {
        List<Station> stationTree = stationService.getStationTree(area);
        return success(stationTree);
    }

}
