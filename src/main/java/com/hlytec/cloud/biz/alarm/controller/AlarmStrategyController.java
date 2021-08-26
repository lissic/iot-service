package com.hlytec.cloud.biz.alarm.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.hlytec.cloud.biz.system.log.annotation.Log;
import com.hlytec.cloud.common.entity.CommonParamVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlytec.cloud.biz.alarm.model.entity.AlarmStrategy;
import com.hlytec.cloud.biz.alarm.model.vo.QueryAlarmStrategyVo;
import com.hlytec.cloud.biz.alarm.service.AlarmItemService;
import com.hlytec.cloud.biz.alarm.service.AlarmStrategyService;
import com.hlytec.cloud.common.controller.BaseController;
import com.hlytec.cloud.common.result.CommonResult;
import com.hlytec.cloud.common.result.PageResult;

/**
 * @description: AlarmStrategyController
 * @author: zero
 * @date: 2021/6/2 10:12
 */
@RestController
@RequestMapping("/net/alarm/strategy")
public class AlarmStrategyController extends BaseController {

    @Autowired
    private AlarmStrategyService strategyService;

    @Autowired
    private AlarmItemService itemService;

    @Log(module = "告警管理", description = "新增告警策略")
    @PostMapping("/save")
    public CommonResult<Object> saveAlarm(@RequestBody @Validated AlarmStrategy strategy) {
        strategyService.save(strategy);
        return success();
    }

    @Log(module = "告警管理", description = "删除告警策略")
    @DeleteMapping("/delete")
    public CommonResult<Object> deleteAlarm(@RequestBody CommonParamVo deleteAlarmStrategyVo) {
        strategyService.deleteAlaStrategy(deleteAlarmStrategyVo);
        return success();
    }

    @Log(module = "告警管理", description = "修改告警策略")
    @PutMapping("/update")
    public CommonResult<Object> updateAlarm(@RequestBody @Validated AlarmStrategy strategy) {
        strategyService.updateById(strategy);
        return success();
    }

    @PostMapping("/list")
    public CommonResult<Object> findList(@RequestBody AlarmStrategy strategy) {
        List<AlarmStrategy> list = strategyService.findList(strategy);
        return success(list);
    }

    @PostMapping("/page")
    public CommonResult<Object> findPage(@RequestBody QueryAlarmStrategyVo strategyVo) {
        Page<AlarmStrategy> page = new Page<>(strategyVo.getPageNo(), strategyVo.getPageSize());
        QueryWrapper<AlarmStrategy> queryWrapper = getAlarmStrategyQueryWrapper(strategyVo);
        PageResult<AlarmStrategy> result = strategyService.findPageWithParam(page, queryWrapper);
        result.getList().stream().parallel().forEach(strategy -> {
            String alarmItems = strategy.getAlarmItems();
            if (StringUtils.isNotEmpty(alarmItems)) {
                List<String> itemIds = Arrays.asList(alarmItems.split(","));
                List<String> itemNames = itemService.getItemNames(itemIds);
                strategy.setItemNameList(itemNames);
            }
        });
        return success(result);
    }

    @GetMapping("/{strategyId}")
    public CommonResult<Object> getAlarmDetail(@PathVariable("strategyId") String strategyId) {
        AlarmStrategy item = strategyService.get(strategyId);
        return success(item);
    }

    private QueryWrapper<AlarmStrategy> getAlarmStrategyQueryWrapper(QueryAlarmStrategyVo strategyVo) {
        QueryWrapper<AlarmStrategy> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(strategyVo.getName())) {
            queryWrapper.like("name", strategyVo.getName());
        }
        if (StringUtils.isNotEmpty(strategyVo.getDescription())) {
            queryWrapper.like("description", strategyVo.getDescription());
        }
        if (Objects.nonNull(strategyVo.getAlarmLevel())) {
            queryWrapper.eq("alarm_level", strategyVo.getAlarmLevel());
        }
        if (Objects.nonNull(strategyVo.getStatus())) {
            queryWrapper.eq("status", strategyVo.getStatus());
        }
        if (Objects.nonNull(strategyVo.getType())) {
            queryWrapper.eq("type", strategyVo.getType());
        }
        queryWrapper.orderByDesc("create_time");
        return queryWrapper;
    }
}
