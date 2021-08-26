package com.hlytec.cloud.common.entity;

import lombok.Data;

/**
 * @description: BaseQueryEntity
 * @author: zero
 * @date: 2021/6/17 10:48
 */
@Data
public class BaseQueryEntity {

    private int pageNo = 1;
    private int pageSize = 20;
}
