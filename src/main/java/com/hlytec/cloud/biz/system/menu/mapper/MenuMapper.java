package com.hlytec.cloud.biz.system.menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hlytec.cloud.biz.system.menu.model.entity.Menu;
import com.hlytec.cloud.biz.system.menu.model.po.MenuTree;
import com.hlytec.cloud.biz.system.menu.model.vo.QueryMenuVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: Permission
 * @author: zero
 * @date: 2021/4/19 16:21
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 查询一级菜单
     * @param available available
     * @return List<MenuTree>
     */
    List<MenuTree> getFirstLevel(Boolean available);

    /**
     * 查询所有子菜单
     * @param parentId parentId
     * @return List<MenuTree>
     */
    List<MenuTree> getSubMenus(@Param("parentId") String parentId);

    /**
     * 查询菜单列表
     * @param queryMenuVo queryMenuVo
     * @return List<MenuTree>
     */
    List<MenuTree> getMenuList(@Param("queryMenuVo") QueryMenuVo queryMenuVo);
}
