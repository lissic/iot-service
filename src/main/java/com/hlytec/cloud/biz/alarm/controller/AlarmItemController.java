package com.hlytec.cloud.biz.alarm.controller;

import java.util.List;
import java.util.Objects;

import com.hlytec.cloud.biz.system.log.annotation.Log;
import com.hlytec.cloud.biz.system.user.model.entity.User;
import com.hlytec.cloud.biz.system.user.service.UserService;
import com.hlytec.cloud.common.entity.CommonParamVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlytec.cloud.biz.alarm.model.entity.AlarmItem;
import com.hlytec.cloud.biz.alarm.model.vo.QueryAlarmItemVo;
import com.hlytec.cloud.biz.alarm.service.AlarmItemService;
import com.hlytec.cloud.common.controller.BaseController;
import com.hlytec.cloud.common.result.CommonResult;
import com.hlytec.cloud.common.result.PageResult;

/**
 * @description: AlarmItemController
 * @author: zero
 * @date: 2021/6/2 10:13
 */
@RestController
@RequestMapping("/net/alarm/item")
public class AlarmItemController extends BaseController {

    @Autowired
    private AlarmItemService alarmItemService;

    @Autowired
    private UserService userService;

    @Log(module = "告警管理", description = "新增告警项")
    @PostMapping("/save")
    public CommonResult<Object> saveAlarmItem(@RequestBody @Validated AlarmItem item) {
        alarmItemService.save(item);
        return success();
    }

    @Log(module = "告警管理", description = "删除告警项")
    @DeleteMapping("/delete")
    public CommonResult<Object> deleteAlarmItem(@RequestBody CommonParamVo deleteAlarmItemVo) {
        alarmItemService.deleteAlarmItem(deleteAlarmItemVo);
        return success();
    }

    @Log(module = "告警管理", description = "修改告警项")
    @PutMapping("/update")
    public CommonResult<Object> updateAlarmItem(@RequestBody @Validated AlarmItem item) {
        alarmItemService.updateById(item);
        return success();
    }

    @PostMapping("/list")
    public CommonResult<Object> findList(@RequestBody AlarmItem item) {
        List<AlarmItem> list = alarmItemService.findList(item);
        return success(list);
    }

    @PostMapping("/page")
    public CommonResult<Object> findPage(@RequestBody QueryAlarmItemVo queryAlarmItemVo) {
        Page<AlarmItem> page = new Page<>(queryAlarmItemVo.getPageNo(), queryAlarmItemVo.getPageSize());
        QueryWrapper<AlarmItem> queryWrapper = getAlarmItemQueryWrapper(queryAlarmItemVo);
        PageResult<AlarmItem> result = alarmItemService.findPageWithParam(page, queryWrapper);
        result.getList().parallelStream().forEach(item -> {
            User user = userService.get(item.getCreateUser());
            item.setCreateUser(user.getLoginName());
        });
        return success(result);
    }

    @GetMapping("/{itemId}")
    public CommonResult<Object> getAlarmItemDetail(@PathVariable("itemId") String itemId) {
        AlarmItem item = alarmItemService.get(itemId);
        return success(item);
    }

    private QueryWrapper<AlarmItem> getAlarmItemQueryWrapper(QueryAlarmItemVo queryAlarmItemVo) {
        QueryWrapper<AlarmItem> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(queryAlarmItemVo.getItemName())) {
            queryWrapper.like("item_name", queryAlarmItemVo.getItemName());
        }
        if (StringUtils.isNotEmpty(queryAlarmItemVo.getItemCode())) {
            queryWrapper.eq("item_code", queryAlarmItemVo.getItemCode());
        }
        if (Objects.nonNull(queryAlarmItemVo.getStandardVal())) {
            queryWrapper.eq("standard_val", queryAlarmItemVo.getStandardVal());
        }
        if (Objects.nonNull(queryAlarmItemVo.getThresholdVal())) {
            queryWrapper.eq("threshold_val", queryAlarmItemVo.getThresholdVal());
        }
        queryWrapper.orderByDesc("create_time");
        return queryWrapper;
    }
}
