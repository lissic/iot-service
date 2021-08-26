package com.hlytec.cloud.biz.system.log.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlytec.cloud.biz.system.log.mapper.LogMapper;
import com.hlytec.cloud.biz.system.log.model.entity.SysLog;
import com.hlytec.cloud.biz.system.log.model.vo.QueryLogVo;
import com.hlytec.cloud.common.result.PageResult;
import com.hlytec.cloud.common.service.BaseService;

/**
 * @description: LogServiceImpl
 * @author: zero
 * @date: 2021/4/28 10:51
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class LogService extends BaseService<LogMapper, SysLog> {

    @Autowired
    private LogMapper logMapper;

    public PageResult<SysLog> queryLogPage(QueryLogVo queryLogVo) {
        Page<SysLog> page = new Page<>(queryLogVo.getPageNo(), queryLogVo.getPageSize());
        QueryWrapper<SysLog> queryWrapper = getSysLogQueryWrapper(queryLogVo, page);
        Page<SysLog> sysLogPage = logMapper.selectPage(page, queryWrapper);
        PageResult<SysLog> result = new PageResult<>(sysLogPage);
        return result;
    }

    private QueryWrapper<SysLog> getSysLogQueryWrapper(QueryLogVo queryLogVo, Page<SysLog> page) {
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem creatTime = OrderItem.desc("create_time");
        orderItems.add(creatTime);
        page.setOrders(orderItems);
        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(queryLogVo.getTitle())) {
            queryWrapper.like("title", queryLogVo.getTitle());
        }
        if (StringUtils.isNotEmpty(queryLogVo.getRequestUrl())) {
            queryWrapper.like("request_url", queryLogVo.getRequestUrl());
        }
        if (StringUtils.isNotEmpty(queryLogVo.getBizType())) {
            queryWrapper.like("biz_type", queryLogVo.getBizType());
        }
        if (StringUtils.isNotEmpty(queryLogVo.getOperator())) {
            queryWrapper.eq("operator", queryLogVo.getOperator());
        }
        if (Objects.nonNull(queryLogVo.getIsException())) {
            queryWrapper.eq("is_exception", queryLogVo.getIsException());
        }
        if (Objects.nonNull(queryLogVo.getExecStartTime()) && Objects.nonNull(queryLogVo.getExecEndTime())) {
            queryWrapper.between("create_time", queryLogVo.getExecStartTime(), queryLogVo.getExecEndTime());
        }
        return queryWrapper;
    }

}
