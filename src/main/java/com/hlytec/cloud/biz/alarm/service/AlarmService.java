package com.hlytec.cloud.biz.alarm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlytec.cloud.biz.alarm.model.entity.AlarmItem;
import com.hlytec.cloud.biz.alarm.model.entity.AlarmStrategy;
import com.hlytec.cloud.biz.alarm.model.po.AlarmPo;
import com.hlytec.cloud.biz.alarm.model.vo.QueryAlarmVo;
import com.hlytec.cloud.biz.system.user.model.entity.User;
import com.hlytec.cloud.biz.system.user.utils.UserUtil;
import com.hlytec.cloud.common.constants.SysConstants;
import com.hlytec.cloud.common.result.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hlytec.cloud.biz.alarm.mapper.AlarmMapper;
import com.hlytec.cloud.biz.alarm.model.entity.Alarm;
import com.hlytec.cloud.common.service.BaseService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description: Alarm
 * @author: zero
 * @date: 2021/6/2 10:10
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class AlarmService extends BaseService<AlarmMapper, Alarm> {

    @Autowired
    private AlarmMapper alarmMapper;

    @Autowired
    private AlarmStrategyService strategyService;

    @Autowired
    private AlarmItemService alarmItemService;

    public PageResult<Alarm>   findAlarmByPage(QueryAlarmVo queryAlarmVo) {
        User currentUser = UserUtil.getCurrentUser();
        queryAlarmVo.setLoginName(currentUser.getLoginName());
        queryAlarmVo.setArea(currentUser.getArea());
        Page<Alarm> page = new Page<>(queryAlarmVo.getPageNo(), queryAlarmVo.getPageSize());
        Page<Alarm> alarmPage = alarmMapper.findAlarm(page, queryAlarmVo);
        alarmPage.getRecords().forEach(alarm -> {
            AlarmStrategy alarmStrategy = strategyService.get(alarm.getAlarmStrategyId());
            if (Objects.nonNull(alarmStrategy)) {
                alarm.setAlarmLevel(alarmStrategy.getAlarmLevel());
            }
            if (SysConstants.SYSTEM_CODE.contains(alarm.getDeviceId())) {
                alarm.setDeviceName("system-server");
            }
        });
        PageResult<Alarm> result = new PageResult<>(alarmPage);
        return result;
    }

    public AlarmPo getAlarmDetails(String alarmId) {
        if (StringUtils.isEmpty(alarmId)) {
            return null;
        }
        AlarmPo alarmPo = new AlarmPo();
        Alarm alarm = get(alarmId);
        BeanUtils.copyProperties(alarm,alarmPo);
        if (StringUtils.isNotEmpty(alarm.getAlarmStrategyId())) {
            AlarmStrategy strategy = strategyService.get(alarm.getAlarmStrategyId());
            alarmPo.setAlarmStrategyId(strategy.getId());
            alarmPo.setAlarmStrategyName(strategy.getName());
            alarmPo.setAlarmLevel(strategy.getAlarmLevel());
        }
        if (StringUtils.isNotEmpty(alarm.getAlarmItemId())) {
            AlarmItem alarmItem = alarmItemService.get(alarm.getAlarmItemId());
            alarmPo.setStandardVal(alarmItem.getStandardVal());
            alarmPo.setThresholdVal(alarmItem.getThresholdVal());
        }
        if (SysConstants.SYSTEM_CODE.contains(alarm.getDeviceId())) {
            alarmPo.setDeviceName("system-server");
        }
        return alarmPo;
    }
}
