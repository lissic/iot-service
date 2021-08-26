package com.hlytec.cloud.biz.device.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hlytec.cloud.biz.device.model.entity.Battery;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: BatteryMapper
 * @author: zero
 * @date: 2021/6/30 11:20
 */
@Mapper
public interface BatteryMapper extends BaseMapper<Battery> {
}
