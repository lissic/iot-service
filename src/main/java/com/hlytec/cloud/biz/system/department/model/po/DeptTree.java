package com.hlytec.cloud.biz.system.department.model.po;

import lombok.Data;

import java.util.List;

/**
 * @description: DeptTree
 * @author: JackChen
 * @date: 2021/6/16 15:08
 */
@Data
public class DeptTree {
    private String id;
    private String parentIds;
    private String parentId;
    private String name;
    private String address;
    private String zipCode;
    private String master;
    private String phone;
    private Boolean avaliable;
    private List<DeptTree> childNode;
}
