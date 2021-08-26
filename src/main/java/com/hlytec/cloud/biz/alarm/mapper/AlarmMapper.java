package com.hlytec.cloud.biz.alarm.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlytec.cloud.biz.alarm.model.vo.QueryAlarmVo;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hlytec.cloud.biz.alarm.model.entity.Alarm;

/**
 * @description: AlarmMapper
 * @author: zero
 * @date: 2021/6/2 10:07
 */
@Mapper
public interface AlarmMapper extends BaseMapper<Alarm> {

    /**
     * 告警分页查询
     * @param page page
     * @param queryAlarmVo queryAlarmVo
     * @return Page<Alarm>
     */
    Page<Alarm> findAlarm(Page<Alarm>page, QueryAlarmVo queryAlarmVo);
}
