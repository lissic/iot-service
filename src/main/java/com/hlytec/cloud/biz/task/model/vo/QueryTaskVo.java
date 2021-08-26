package com.hlytec.cloud.biz.task.model.vo;

import com.hlytec.cloud.common.entity.BaseQueryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: QueryTaskVo
 * @author: zero
 * @date: 2021/6/24 17:30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryTaskVo extends BaseQueryEntity {
    private String taskName;
    private Integer status;
}
