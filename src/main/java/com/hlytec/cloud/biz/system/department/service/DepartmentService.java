package com.hlytec.cloud.biz.system.department.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hlytec.cloud.biz.system.department.mapper.DepartmentMapper;
import com.hlytec.cloud.biz.system.department.model.entity.Department;
import com.hlytec.cloud.biz.system.department.model.po.DeptTree;
import com.hlytec.cloud.biz.system.department.model.vo.QueryDeptVo;
import com.hlytec.cloud.biz.system.department.utils.DeptTreeNode;
import com.hlytec.cloud.common.entity.CommonParamVo;
import com.hlytec.cloud.common.result.PageResult;
import com.hlytec.cloud.common.service.BaseService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: DepartmentService
 * @author: zero
 * @date: 2021/5/26 10:56
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class DepartmentService extends BaseService<DepartmentMapper, Department> {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Transactional(readOnly = false)
    public void updateOne(QueryDeptVo queryDeptVo){
        departmentMapper.updateStatusById(queryDeptVo);
    }

    public Department findDept(String name){
        Department dep = departmentMapper.getDeptByName(name);
        return dep;
    }

    public List<DeptTree> findDeptList(QueryDeptVo queryDeptVo) {
        List<DeptTree> deptTreeList = new ArrayList<>();
        List<DeptTree> deptList = departmentMapper.getDepts(queryDeptVo);
        List<DeptTree> deptTrees = getDeptTree(deptList);
        if (queryDeptVo.getName()==null
                &&queryDeptVo.getZipCode()==null
                &&queryDeptVo.getMaster()==null
                &&queryDeptVo.getAvaliable()==null){
            deptTrees.forEach(dept->{
                if ("0".equals(dept.getParentId())){
                    deptTreeList.add(dept);

                }
            });
            return deptTreeList;
        }
        else {
            return deptTrees;
        }
    }

    public PageResult<Department> findDeptPage(QueryDeptVo queryDeptVo){
        List<Department> nodeList = departmentMapper.getDeptList(queryDeptVo);
        List<Department> deptList = DeptTreeNode.traverseDeptTree(nodeList,"0");
        int pageNo = queryDeptVo.getPageNo();
        int pageSize = queryDeptVo.getPageSize();
        int total = deptList.size();
        List<Department> departmentList = deptList.stream().skip((pageNo-1)*pageSize).limit(pageSize).collect(Collectors.toList());
        PageResult<Department> result = new PageResult<>(pageNo,pageSize,total);
        result.setList(departmentList);
        return result;
    }

    @Transactional(readOnly = false)
    public String deleteDept(CommonParamVo deleteDeptVo){
        String result = "";
        List<String> ids = new ArrayList<>();
        List<String> deptIds = deleteDeptVo.getIds();
        for (String deptId : deptIds) {
            if (findByPid(deptId).size() == 0) {
                ids.add(deptId);
            } else {
                result = "批量删除失败！";
                break;
            }
        }
        if (ids.size()>0){
            this.batchDelete(ids);
        }
        return result;
    }

    public List<Department> findByPid(String parentId){
        return departmentMapper.selectList(new QueryWrapper<Department>().eq("parent_id",parentId));
    }

    public List<DeptTree> getDeptTree(String deptId) {
        List<DeptTree> queryList = new ArrayList<>();
        if (StringUtils.isEmpty(deptId)) {
            queryList = departmentMapper.getParentDept();
        } else {
            Department department = departmentMapper.selectById(deptId);
            DeptTree deptTree = new DeptTree();
            BeanUtils.copyProperties(department, deptTree);
            deptTree.setId(deptId);
            queryList.add(deptTree);
        }
        return getDeptTree(queryList);
    }

    public List<DeptTree> getDeptTree(List<DeptTree> depts) {
        List<DeptTree> result = new ArrayList<>();
        depts.forEach(dept -> {
            List<DeptTree> subDepts = departmentMapper.getAllSubDepts(dept.getId());
            if (CollectionUtils.isNotEmpty(subDepts)) {
                List<DeptTree> menuTree = getDeptTree(subDepts);
                dept.setChildNode(menuTree);
            }
            result.add(dept);
        });
        return result;
    }
}
