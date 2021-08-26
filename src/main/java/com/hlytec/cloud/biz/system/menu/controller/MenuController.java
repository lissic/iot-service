package com.hlytec.cloud.biz.system.menu.controller;

import java.util.List;

import com.hlytec.cloud.biz.system.log.annotation.Log;
import com.hlytec.cloud.common.entity.CommonParamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.hlytec.cloud.biz.system.menu.model.entity.Menu;
import com.hlytec.cloud.biz.system.menu.model.po.MenuTree;
import com.hlytec.cloud.biz.system.menu.model.vo.QueryMenuVo;
import com.hlytec.cloud.biz.system.menu.service.MenuService;
import com.hlytec.cloud.common.controller.BaseController;
import com.hlytec.cloud.common.result.CommonResult;
import com.hlytec.cloud.common.result.PageResult;

/**
 * @description: MenuController
 * @author: zero
 * @date: 2021/4/29 10:39
 */
@RestController
@RequestMapping("/sys/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    @PostMapping("/save")
    public CommonResult<Object> saveMenu(@RequestBody @Validated Menu menu) {
        String menuId = menuService.save(menu);
        return success(menuId);
    }

    @DeleteMapping("/delete")
    public CommonResult<Object> deleteMenu(@RequestBody CommonParamVo deleteMenuVo) {
        menuService.deleteMenu(deleteMenuVo);
        return success();
    }

    @Log(module = "菜单管理", description = "更新")
    @PutMapping("/update")
    public CommonResult<Object> updateMenu(@RequestBody Menu menu) {
        menuService.updateById(menu);
        return success();
    }

    @GetMapping("/{menuId}")
    public CommonResult<Object> findDetails(@PathVariable("menuId") String menuId) {
        Menu menu = menuService.get(menuId);
        return success(menu);
    }

    @GetMapping("/findMenus/{parentId}")
    public CommonResult<Object> findMenus(@PathVariable("parentId") String parentId){
        List<Menu> menuList = menuService.findByPid(parentId);
        return success(menuList);
    }

    @PostMapping("/list")
    public CommonResult<Object> findList(@RequestBody QueryMenuVo menu) {
        List<MenuTree> list = menuService.findMenuList(menu);
        return success(list);
    }

    @PostMapping("/page")
    public CommonResult<Object> query(@RequestBody QueryMenuVo menu) {
        PageResult<Menu> result = menuService.findMenuPage(menu);
        return success(result);
    }

    @PostMapping("/tree")
    public CommonResult<Object> queryMenuTree(String menuId) {
        List<MenuTree> menuTree = menuService.getMenuTree(menuId, true);
        return success(menuTree);
    }
}
