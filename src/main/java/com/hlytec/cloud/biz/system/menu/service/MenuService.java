package com.hlytec.cloud.biz.system.menu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlytec.cloud.biz.system.menu.mapper.MenuMapper;
import com.hlytec.cloud.biz.system.menu.model.entity.Menu;
import com.hlytec.cloud.biz.system.menu.model.po.MenuTree;
import com.hlytec.cloud.biz.system.menu.model.vo.QueryMenuVo;
import com.hlytec.cloud.common.entity.CommonParamVo;
import com.hlytec.cloud.common.exception.NetServiceException;
import com.hlytec.cloud.common.result.PageResult;
import com.hlytec.cloud.common.service.BaseService;

/**
 * @description: MenuService
 * @author: zero
 * @date: 2021/4/29 14:50
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class MenuService extends BaseService<MenuMapper, Menu> {

    @Autowired
    private MenuMapper menuMapper;

    @Transactional(readOnly = false)
    public void deleteMenu(CommonParamVo deleteMenuVo){
        List<String> ids = new ArrayList<>();
        List<String> menuIds = deleteMenuVo.getIds();
        menuIds.forEach(menuId->{
            if (CollectionUtils.isEmpty(findByPid(menuId))){
                ids.add(menuId);
            } else {
                throw new NetServiceException("exit children menu, please delete child menu first.");
            }
        });
        this.batchDelete(ids);
    }

    public List<Menu> findByPid(String parentId){
        return menuMapper.selectList(new QueryWrapper<Menu>().eq("parent_id",parentId));
    }

    public List<MenuTree> findMenuList(QueryMenuVo queryMenuVo) {
        if (StringUtils.isEmpty(queryMenuVo.getName())
                && StringUtils.isEmpty(queryMenuVo.getUrl())
                && StringUtils.isEmpty(queryMenuVo.getResourceType())) {
            return getMenuTree("", queryMenuVo.getAvailable());
        } else {
            return menuMapper.getMenuList(queryMenuVo);
        }

    }

    public PageResult<Menu> findMenuPage(QueryMenuVo queryMenuVo) {
        Page<Menu> page = new Page<>(queryMenuVo.getPageNo(), queryMenuVo.getPageSize());
        QueryWrapper<Menu> query = buildQueryParam(
                queryMenuVo.getName(),
                queryMenuVo.getUrl(),
                queryMenuVo.getResourceType(),
                queryMenuVo.getParentId(),
                queryMenuVo.getAvailable());
        query.orderByDesc("create_time");
        Page<Menu> menuPage = menuMapper.selectPage(page, query);
        PageResult<Menu> result = new PageResult<>(menuPage);
        return result;
    }

    private QueryWrapper<Menu> buildQueryParam(String name, String url, String resourceType, String parentId, Boolean available) {
        QueryWrapper<Menu> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(name)) {
            query.like("name", name);
        }
        if (StringUtils.isNotEmpty(url)) {
            query.like("url", url);
        }
        if (StringUtils.isNotEmpty(resourceType)) {
            query.eq("resource_type", resourceType);
        }
        if (StringUtils.isNotEmpty(parentId)) {
            query.eq("parent_id", parentId);
        }
        if (available != null) {
            query.eq("available", available);
        }
        return query;
    }

    public List<MenuTree> getMenuTree(String menuId, Boolean available) {
        List<MenuTree> queryList = new ArrayList<>();
        if (StringUtils.isEmpty(menuId)) {
            queryList = menuMapper.getFirstLevel(available);
        } else {
            Menu menu = menuMapper.selectById(menuId);
            MenuTree menuTree = new MenuTree();
            BeanUtils.copyProperties(menu, menuTree);
            menuTree.setId(menuId);
            queryList.add(menuTree);
        }
        return getMenuTree(queryList);
    }

    public List<MenuTree> getMenuTree(List<MenuTree> menus) {
        List<MenuTree> result = new ArrayList<>();
        menus.forEach(menu -> {
            List<MenuTree> subMenus = menuMapper.getSubMenus(menu.getId());
            if (CollectionUtils.isNotEmpty(subMenus)) {
                List<MenuTree> menuTree = getMenuTree(subMenus);
                menu.setSubMenu(menuTree);
            }
            result.add(menu);
        });
        return result;
    }

}
