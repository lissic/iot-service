package com.hlytec.cloud.biz.device.model.vo;

import com.hlytec.cloud.common.entity.BaseQueryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zero
 * @description QueryDeviceSoftVo
 * @date 2021/7/30 14:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryDeviceSoftVo extends BaseQueryEntity {
    private String name;
    private Integer upgradeType;
}
