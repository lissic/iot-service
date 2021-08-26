package com.hlytec.cloud.biz.inspection.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlytec.cloud.biz.inspection.mapper.InspectionMapper;
import com.hlytec.cloud.biz.inspection.model.entity.Inspection;
import com.hlytec.cloud.biz.inspection.model.vo.QueryInspectionVo;
import com.hlytec.cloud.common.result.PageResult;
import com.hlytec.cloud.common.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @description: InspectionService
 * @author: zero
 * @date: 2021/6/30 17:34
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class InspectionService extends BaseService<InspectionMapper, Inspection> {

    @Autowired
    private InspectionMapper inspectionMapper;

    public PageResult<Inspection> findInspectionPage(QueryInspectionVo inspectionVo) {
        Page<Inspection> page = new Page<>(inspectionVo.getPageNo(),inspectionVo.getPageSize());
        QueryWrapper<Inspection> param = buildQueryParam(inspectionVo);
        Page<Inspection> inspectionPage = inspectionMapper.selectPage(page, param);
        return new PageResult<>(inspectionPage);
    }

    public List<Inspection> findInspectionList(QueryInspectionVo inspectionVo) {
        QueryWrapper<Inspection> param = buildQueryParam(inspectionVo);
        List<Inspection> inspections = inspectionMapper.selectList(param);
        return inspections;
    }
    private QueryWrapper<Inspection> buildQueryParam(QueryInspectionVo inspectionVo) {
        QueryWrapper<Inspection> param = new QueryWrapper<>();
        param.orderByAsc("status,create_time");
        if (StringUtils.isNotEmpty(inspectionVo.getName())) {
            param.eq("name", inspectionVo.getName());
        }
        if (StringUtils.isNotEmpty(inspectionVo.getCheckPerson())) {
            param.eq("check_person", inspectionVo.getCheckPerson());
        }
        if (Objects.nonNull(inspectionVo.getStartTime()) && Objects.nonNull(inspectionVo.getEndTime())) {
            param.between("create_time", inspectionVo.getStartTime(), inspectionVo.getEndTime());
        }
        if (Objects.nonNull(inspectionVo.getStatus())) {
            param.eq("status", inspectionVo.getStatus());
        }
        param.orderByDesc("create_time");
        param.orderByAsc("status");
        return param;
    }
}
