package com.hlytec.cloud.biz.alarm.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.hlytec.cloud.common.entity.CommonParamVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hlytec.cloud.biz.alarm.mapper.AlarmStrategyMapper;
import com.hlytec.cloud.biz.alarm.model.entity.AlarmStrategy;
import com.hlytec.cloud.common.service.BaseService;

/**
 * @description: AlarmStrategyService
 * @author: zero
 * @date: 2021/6/2 10:08
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class AlarmStrategyService extends BaseService<AlarmStrategyMapper, AlarmStrategy> {
    @Transactional(readOnly = false)
    public void deleteAlaStrategy(CommonParamVo deleteAlarmStrategyVo){
        List<String> strategyIds = deleteAlarmStrategyVo.getIds();
        this.batchDelete(strategyIds);
    }

    public List<AlarmStrategy> getStrategyByItem(String alarmItemId) {
        AlarmStrategy param = AlarmStrategy.builder().status(true).build();
        List<AlarmStrategy> collect = findList(param).stream().filter(strategy -> {
            List<String> itemCodes = Arrays.asList(strategy.getAlarmItems().split(","));
            if (itemCodes.contains(alarmItemId)) {
                return true;
            } else {
                return false;
            }
        }).collect(Collectors.toList());
        return collect;
    }
}
