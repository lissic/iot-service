package com.hlytec.cloud.biz.system.department.utils;

import com.hlytec.cloud.biz.system.department.model.entity.Department;

import java.util.LinkedList;
import java.util.List;

/**
 * 递归遍历部门树
 *
 * @description: DeptTreeNode
 * @author: JackChen
 * @date: 2021/6/3 14:37
 */
public class DeptTreeNode{

    public static List<Department> traverseDeptTree(List<Department> nodeList, String parentId) {
        List<Department> list = new LinkedList<>();
        for (Department dept : nodeList) {
            if (dept.getParentId().equals(parentId)) {
                dept.setChildNode(traverseDeptTree(nodeList,dept.getId()));
                list.add(dept);
            }
        }
        return list;
    }
}
