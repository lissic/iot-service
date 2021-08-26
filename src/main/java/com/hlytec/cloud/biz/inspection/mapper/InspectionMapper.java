package com.hlytec.cloud.biz.inspection.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hlytec.cloud.biz.inspection.model.entity.Inspection;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: InspectionMapper
 * @author: zero
 * @date: 2021/6/30 17:33
 */
@Mapper
public interface InspectionMapper extends BaseMapper<Inspection> {
}
