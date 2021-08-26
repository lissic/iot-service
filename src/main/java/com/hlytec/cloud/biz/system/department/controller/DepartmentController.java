package com.hlytec.cloud.biz.system.department.controller;

import java.util.List;

import com.hlytec.cloud.common.entity.CommonParamVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.hlytec.cloud.biz.system.department.model.entity.Department;
import com.hlytec.cloud.biz.system.department.model.po.DeptTree;
import com.hlytec.cloud.biz.system.department.model.vo.QueryDeptVo;
import com.hlytec.cloud.biz.system.department.service.DepartmentService;
import com.hlytec.cloud.common.controller.BaseController;
import com.hlytec.cloud.common.result.CommonResult;
import com.hlytec.cloud.common.result.PageResult;

/**
 * @description: DepartmentController
 * @author: zero
 * @date: 2021/5/24 10:16
 */
@RestController
@RequestMapping("/sys/dept")
public class DepartmentController extends BaseController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/save")
    public CommonResult<Object> saveDept(@RequestBody @Validated Department dept) {
        Department department = departmentService.findDept(dept.getName());
        if (department == null){
            departmentService.save(dept);
            return success();
        }else {
            return fail("该部门已存在！");
        }

    }

    @DeleteMapping("/delete")
    public CommonResult<Object> deleteDept(@RequestBody CommonParamVo deleteDeptVo) {
        String result = departmentService.deleteDept(deleteDeptVo);
        if (StringUtils.isEmpty(result)){
            return success("删除成功！");
        }else {
            return fail("所选部门下不能包含子级部门,无法删除！");
        }
    }

    @PutMapping("/update")
    public CommonResult<Object> updateDept(@RequestBody @Validated Department dept) {
        departmentService.updateById(dept);
        return success();
    }

    @PostMapping("/updateStatus")
    public CommonResult<Object>  updateStatus(@RequestBody QueryDeptVo queryDeptVo){
        departmentService.updateOne(queryDeptVo);
        return success();
    }

    @GetMapping("/{deptId}")
    public CommonResult<Object> findDetails(@PathVariable("deptId") String deptId) {
        Department dept = departmentService.get(deptId);
        return success(dept);
    }

    @GetMapping("/findByPid/{parentId}")
    public CommonResult<Object> findDepts(@PathVariable("parentId") String parentId){
        List<Department> deptList = departmentService.findByPid(parentId);
        return success(deptList);
    }

    @PostMapping("/page")
    public CommonResult<Object> findPage(@RequestBody QueryDeptVo queryDeptVo) {
        PageResult<Department> deptPage = departmentService.findDeptPage(queryDeptVo);
        return success(deptPage);
    }

    @PostMapping("/list")
    public CommonResult<Object> findList(@RequestBody QueryDeptVo queryDeptVo){
        List<DeptTree> deptTreeList = departmentService.findDeptList(queryDeptVo);
        return success(deptTreeList);
    }

    @PostMapping("/tree")
    public CommonResult<Object> queryDeptTree(String deptId) {
        List<DeptTree> deptTreeList = departmentService.getDeptTree(deptId);
        return success(deptTreeList);
    }
}
