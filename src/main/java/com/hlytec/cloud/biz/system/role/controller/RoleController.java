package com.hlytec.cloud.biz.system.role.controller;

import java.util.List;
import java.util.Objects;

import com.hlytec.cloud.biz.system.log.annotation.Log;
import com.hlytec.cloud.common.result.ResultEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.hlytec.cloud.biz.system.role.model.entity.Role;
import com.hlytec.cloud.biz.system.role.model.vo.QueryRoleVo;
import com.hlytec.cloud.biz.system.role.model.vo.RoleVo;
import com.hlytec.cloud.biz.system.role.service.RoleService;
import com.hlytec.cloud.common.controller.BaseController;
import com.hlytec.cloud.common.entity.CommonParamVo;
import com.hlytec.cloud.common.result.CommonResult;
import com.hlytec.cloud.common.result.PageResult;

/**
 * @description: RoleController
 * @author: zero
 * @date: 2021/4/29 10:40
 */
@RestController
@RequestMapping("/sys/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @Log(module = "角色管理", description = "新增")
    @PostMapping("/save")
    public CommonResult<Object> saveRole(@RequestBody @Validated RoleVo role)  {
        Role isExit = roleService.get(Role.builder().role(role.getRole()).build());
        if (Objects.nonNull(isExit)) {
            return fail(ResultEnum.ROLE_IS_EXITS);
        }
        String roleId = roleService.saveRole(role);
        return success(roleId);
    }

    @Log(module = "角色管理", description = "删除")
    @DeleteMapping("/delete")
    public CommonResult<Object> deleteRole(@RequestBody CommonParamVo deleteRoleVo) {
        roleService.deleteRole(deleteRoleVo);
        return success();
    }

    @Log(module = "角色管理", description = "更新")
    @PutMapping("/update")
    public CommonResult<Object> updateRole(@RequestBody @Validated RoleVo role) {
        Role isExit = roleService.get(Role.builder().role(role.getRole()).build());
        if (Objects.nonNull(isExit) && !StringUtils.equals(isExit.getId(), role.getId())) {
            return fail(ResultEnum.ROLE_IS_EXITS);
        }
        roleService.updateRole(role);
        return success();
    }

    @GetMapping("/{roleId}")
    public CommonResult<Object> queryRole(@PathVariable("roleId") String roleId) {
        Role role = roleService.getRole(roleId);
        return success(role);
    }

    @PostMapping("/page")
    public CommonResult<Object> queryRolesPage(@RequestBody(required = false) QueryRoleVo queryRoleVo) {
        PageResult<Role> result = roleService.query(queryRoleVo);
        return success(result);
    }

    @PostMapping("/list")
    public CommonResult<Object> queryRolesList(@RequestBody(required = false) QueryRoleVo queryRoleVo) {
        List<Role> roles = roleService.queryList(queryRoleVo);
        return success(roles);
    }
}
