package com.hlytec.cloud.biz.alarm.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hlytec.cloud.biz.alarm.model.entity.AlarmItem;

/**
 * @description: AlarmItemMapper
 * @author: zero
 * @date: 2021/6/2 10:06
 */
@Mapper
public interface AlarmItemMapper extends BaseMapper<AlarmItem> {
}
