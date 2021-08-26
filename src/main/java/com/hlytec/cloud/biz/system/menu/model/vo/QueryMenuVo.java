package com.hlytec.cloud.biz.system.menu.model.vo;

import com.hlytec.cloud.common.entity.BaseQueryEntity;
import lombok.Data;

/**
 * @description: QueryMenuVo
 * @author: zero
 * @date: 2021/6/8 13:37
 */
@Data
public class QueryMenuVo extends BaseQueryEntity {
    private String name;
    private String resourceType;
    private String url;
    private String parentId;
    private Boolean available = true;
}
