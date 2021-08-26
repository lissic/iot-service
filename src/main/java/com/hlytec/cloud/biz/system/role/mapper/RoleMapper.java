package com.hlytec.cloud.biz.system.role.mapper;

import java.util.List;

import com.hlytec.cloud.biz.system.role.model.vo.QueryRoleVo;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlytec.cloud.biz.system.role.model.entity.Role;
import com.hlytec.cloud.biz.system.role.model.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;

/**
 * @description: RoleMapper
 * @author: zero
 * @date: 2021/4/19 16:20
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 查询角色列表
     * @param queryRole queryRole
     * @return Page<Role>
     */
    List<Role> queryRoleList(@Param("queryRole") QueryRoleVo queryRole);

    /**
     * 添加角色菜单关联
     * @param roleMenuList roleMenuList
     */
    void insertRoleMenu(List<RoleMenu> roleMenuList);

    /**
     * 删除角色菜单关联
     * @param roleIds roleIds
     */
    void deleteRoleMenu(List<String> roleIds);

    /**
     * 更新角色菜单关联
     * @param roleId roleId
     * @param menus menus
     */
    void updateRoleMenu(String roleId, List<String> menus);

    /**
     * 获取角色菜单关联
     * @param roleId roleId
     * @return String
     */
    List<String> getRoleMenu(String roleId);

    /**
     * 获取用户关联角色
     * @param roleId roleId
     * @return List<String>
     */
    List<String> getUserRole(String roleId);
}
