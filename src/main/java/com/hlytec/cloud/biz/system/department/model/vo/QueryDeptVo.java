package com.hlytec.cloud.biz.system.department.model.vo;

import com.hlytec.cloud.biz.system.department.model.entity.Department;
import com.hlytec.cloud.common.entity.BaseQueryEntity;
import lombok.Data;

import java.util.List;

/**
 * @description: QueryDeptVo
 * @author: JackChen
 * @date: 2021/6/15 17:05
 */
@Data
public class QueryDeptVo extends BaseQueryEntity {
    private String id;
    private String parentIds;
    private String parentId;
    private String name;
    private String address;
    private String zipCode;
    private String master;
    private String phone;
    private Boolean avaliable;
    private List<Department> childNode;
}
