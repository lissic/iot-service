package com.hlytec.cloud.biz.task.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hlytec.cloud.biz.task.model.entity.Task;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: TaskMapper
 * @author: zero
 * @date: 2021/6/2 17:51
 */
@Mapper
public interface TaskMapper extends BaseMapper<Task> {
}
