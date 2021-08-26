package com.hlytec.cloud.biz.system.department.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hlytec.cloud.biz.system.department.model.entity.Department;
import com.hlytec.cloud.biz.system.department.model.po.DeptTree;
import com.hlytec.cloud.biz.system.department.model.vo.QueryDeptVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: Permission
 * @author: zero
 * @date: 2021/4/19 16:21
 */
@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * 查询父级部门
     *
     * @return list
     */
    List<DeptTree> getParentDept();

    /**
     * 查询所有子部门
     *
     * @param parentId parentId
     * @return list
     */
    List<DeptTree> getAllSubDepts(@Param("parentId") String parentId);

    List<Department> getDeptList(QueryDeptVo queryDeptVo);

    List<DeptTree> getDepts(QueryDeptVo queryDeptVo);

    Department getDeptByName(String deptName);

    void updateStatusById(QueryDeptVo queryDeptVo);

}
