package com.hlytec.cloud.biz.alarm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.hlytec.cloud.biz.alarm.model.entity.Alarm;
import com.hlytec.cloud.biz.alarm.model.po.AlarmPo;
import com.hlytec.cloud.biz.alarm.model.vo.QueryAlarmVo;
import com.hlytec.cloud.biz.alarm.model.vo.UpdateAlarmVo;
import com.hlytec.cloud.biz.alarm.service.AlarmService;
import com.hlytec.cloud.biz.system.log.annotation.Log;
import com.hlytec.cloud.common.controller.BaseController;
import com.hlytec.cloud.common.result.CommonResult;
import com.hlytec.cloud.common.result.PageResult;

/**
 * @description: AlarmController
 * @author: zero
 * @date: 2021/6/2 10:14
 */
@RestController
@RequestMapping("/net/alarm")
public class AlarmController extends BaseController {

    @Autowired
    private AlarmService alarmService;

    @Log(module = "告警管理", description = "处理告警")
    @PutMapping("/updateStatus")
    public CommonResult<Object> updateAlarmStatus(@RequestBody  @Validated UpdateAlarmVo updateAlarmVo) {
        Alarm alarm = Alarm.builder().id(updateAlarmVo.getAlarmId())
                .status(updateAlarmVo.getStatus())
                .handleRes(updateAlarmVo.getHandleRes()).build();
        alarmService.updateById(alarm);
        return success();
    }

    @PostMapping("/page")
    public CommonResult<Object> findPage(@RequestBody QueryAlarmVo queryAlarmVo) {
        PageResult<Alarm> result = alarmService.findAlarmByPage(queryAlarmVo);
        return success(result);
    }

    @GetMapping("/{alarmId}")
    public CommonResult<Object> getAlarmDetail(@PathVariable("alarmId") String alarmId) {
        AlarmPo alarmDetails = alarmService.getAlarmDetails(alarmId);
        return success(alarmDetails);
    }
}
