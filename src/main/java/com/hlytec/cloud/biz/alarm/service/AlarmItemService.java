package com.hlytec.cloud.biz.alarm.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hlytec.cloud.biz.alarm.mapper.AlarmItemMapper;
import com.hlytec.cloud.biz.alarm.model.entity.AlarmItem;
import com.hlytec.cloud.common.entity.CommonParamVo;
import com.hlytec.cloud.common.service.BaseService;

/**
 * @description: AlarmItemService
 * @author: zero
 * @date: 2021/6/2 10:10
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class AlarmItemService extends BaseService<AlarmItemMapper, AlarmItem> {

    @Autowired
    private AlarmItemMapper itemMapper;

    @Transactional(readOnly = false)
    public void deleteAlarmItem(CommonParamVo deleteAlarmItemVo){
        List<String> itemIds = deleteAlarmItemVo.getIds();
        this.batchDelete(itemIds);
    }

    public List<String> getItemNames(List<String> itemIds) {
        List<String> names = this.getItems(itemIds).stream().map(AlarmItem::getItemName).collect(Collectors.toList());
        return names;
    }

    public List<AlarmItem> getItems(List<String> itemIds) {
        List<AlarmItem> alarmItems = itemMapper.selectBatchIds(itemIds);
        return alarmItems;
    }
}
