package com.hlytec.cloud.biz.system.role.model.vo;

import lombok.Data;

/**
 * @description: QueryRoleVo
 * @author: zero
 * @date: 2021/6/8 14:54
 */
@Data
public class QueryRoleVo {
    private String role;
    private String description;
    private Boolean available;
    private String remarks;
    private Integer pageNo;
    private Integer pageSize;
}
