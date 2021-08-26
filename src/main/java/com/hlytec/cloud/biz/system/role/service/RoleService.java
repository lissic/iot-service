package com.hlytec.cloud.biz.system.role.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.hlytec.cloud.common.entity.CommonParamVo;
import com.hlytec.cloud.common.exception.NetServiceException;
import com.hlytec.cloud.common.result.ResultEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hlytec.cloud.biz.system.menu.mapper.MenuMapper;
import com.hlytec.cloud.biz.system.menu.model.entity.Menu;
import com.hlytec.cloud.biz.system.role.mapper.RoleMapper;
import com.hlytec.cloud.biz.system.role.model.entity.Role;
import com.hlytec.cloud.biz.system.role.model.entity.RoleMenu;
import com.hlytec.cloud.biz.system.role.model.vo.QueryRoleVo;
import com.hlytec.cloud.biz.system.role.model.vo.RoleVo;
import com.hlytec.cloud.common.result.PageResult;
import com.hlytec.cloud.common.service.BaseService;

/**
 * @description: RoleServiceImpl
 * @author: zero
 * @date: 2021/4/29 14:50
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class RoleService extends BaseService<RoleMapper, Role> {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuMapper menuMapper;

    public PageResult<Role> query(QueryRoleVo queryRoleVo) {
        List<Role> query = roleMapper.queryRoleList(queryRoleVo);
        // 重新计算分页
        int total = query.size();
        int current = queryRoleVo.getPageNo();
        int size = queryRoleVo.getPageSize();
        List<Role> collect = query.stream().skip((current - 1) * size).limit(size).collect(Collectors.toList());
        PageResult<Role> result = new PageResult<>(current,size, total);
        result.setList(collect);
        return result;
    }

    public List<Role> queryList(QueryRoleVo queryRoleVo) {
        List<Role> roles = roleMapper.queryRoleList(queryRoleVo);
        return roles;
    }

    @Transactional(readOnly = false)
    public String saveRole(RoleVo roleVo) {
        Role role = Role.builder().build();
        BeanUtils.copyProperties(roleVo, role, "menus");
        this.save(role);
        saveRoleMenus(roleVo, role.getId());
        return role.getId();
    }

    private void saveRoleMenus(RoleVo roleVo, String roleId) {
        List<String> menus = roleVo.getMenus();
        List<RoleMenu> roleMenuList = new ArrayList<>(menus.size());
        if (CollectionUtils.isNotEmpty(menus)) {
            menus.forEach(menuId -> {
                RoleMenu build = RoleMenu.builder().roleId(roleId).menuId(menuId).build();
                roleMenuList.add(build);
            });
            roleMapper.insertRoleMenu(roleMenuList);
        }
    }

    @Transactional(readOnly = false)
    public void deleteRole(List<String> ids) {
        this.batchDelete(ids);
        roleMapper.deleteRoleMenu(ids);
    }

    @Transactional(readOnly = false)
    public void updateRole(RoleVo roleVo) {
        Role role = Role.builder().build();
        BeanUtils.copyProperties(roleVo, role, "menus");
        roleMapper.update(role, new UpdateWrapper<Role>().eq("id", role.getId()));
        updateRoleMenu(roleVo);
    }

    private void updateRoleMenu(RoleVo roleVo) {
        List<String> menus = roleVo.getMenus();
        List<RoleMenu> roleMenuList = new ArrayList<>(menus.size());
        if (CollectionUtils.isNotEmpty(menus)) {
            menus.forEach(menuId -> {
                RoleMenu build = RoleMenu.builder().roleId(roleVo.getId()).menuId(menuId).build();
                roleMenuList.add(build);
            });
            roleMapper.deleteRoleMenu(Collections.singletonList(roleVo.getId()));
            roleMapper.insertRoleMenu(roleMenuList);
        }
    }

    public Role getRole(String roleId) {
        Role role = this.get(roleId);
        List<String> menuIds = roleMapper.getRoleMenu(roleId);
        if (Objects.nonNull(role) && CollectionUtils.isNotEmpty(menuIds)) {
            List<Menu> menus = menuMapper.selectBatchIds(menuIds);
            List<Menu> collect = menus.stream().filter(Menu::getAvailable).collect(Collectors.toList());
            role.setMenus(collect);
        }
        return role;
    }

    @Transactional(readOnly = false)
    public void deleteRole(CommonParamVo deleteRoleVo){
        List<String> notDelList = new ArrayList<>();
        List<String> roleIds = deleteRoleVo.getIds();
        roleIds.forEach(roleId -> {
            List<String> userRole = roleMapper.getUserRole(roleId);
            if(CollectionUtils.isNotEmpty(userRole)) {
                notDelList.add(roleId);
            }
        });
        if (CollectionUtils.isNotEmpty(notDelList)) {
            throw new NetServiceException(ResultEnum.ROLE_BIND_USER);
        }
        this.batchDelete(roleIds);
        roleMapper.deleteRoleMenu(roleIds);
    }
}
