package com.hlytec.cloud.biz.system.setting.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlytec.cloud.biz.system.setting.mapper.SettingMapper;
import com.hlytec.cloud.biz.system.setting.model.entity.Setting;
import com.hlytec.cloud.biz.system.setting.model.vo.QuerySettingVo;
import com.hlytec.cloud.common.result.PageResult;
import com.hlytec.cloud.common.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: SettingService
 * @author: JackChen
 * @date: 2021/6/11 16:36
 */
@Service
@Transactional(readOnly = true)
public class SettingService extends BaseService<SettingMapper, Setting> {

    @Autowired
    private SettingMapper settingMapper;

    public PageResult<Setting> findSettingPage(QuerySettingVo querySettingVo){
        Page<Setting> page = new Page<>(querySettingVo.getPageNo(),querySettingVo.getPageSize());
        QueryWrapper<Setting> query = buildQueryParam(
                querySettingVo.getSettingOption(),
                querySettingVo.getSettingContent());
        Page<Setting> settingPage = settingMapper.selectPage(page,query);
        PageResult<Setting> result = new PageResult<>(settingPage);
        return result;
    }

    private QueryWrapper<Setting> buildQueryParam(String settingOption, String settingContent) {
        QueryWrapper<Setting> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(settingOption)) {
            query.like("set_option", settingOption);
        }
        if (StringUtils.isNotEmpty(settingContent)) {
            query.eq("set_content", settingContent);
        }
        return query;
    }
}
