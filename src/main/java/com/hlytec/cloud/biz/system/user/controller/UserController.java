package com.hlytec.cloud.biz.system.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hlytec.cloud.common.result.ResultEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.hlytec.cloud.biz.system.log.annotation.Log;
import com.hlytec.cloud.biz.system.user.model.entity.User;
import com.hlytec.cloud.biz.system.user.model.vo.QueryUserVo;
import com.hlytec.cloud.biz.system.user.model.vo.UserVo;
import com.hlytec.cloud.biz.system.user.service.UserService;
import com.hlytec.cloud.common.controller.BaseController;
import com.hlytec.cloud.common.entity.CommonParamVo;
import com.hlytec.cloud.common.result.CommonResult;
import com.hlytec.cloud.common.result.PageResult;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;


/**
 * @description: UserController
 * @author: zero
 * @date: 2021/4/15 11:41
 */
@RestController
@RequestMapping("/sys/user")
@Slf4j
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Log(module = "用户管理", description = "新增用户")
    @PostMapping("/save")
    public CommonResult<Object> saveUser(@RequestBody @Validated UserVo user) {
        if (StringUtils.isEmpty(user.getPassword())) {
            return fail(ResultEnum.PARAM_INVALIDATE);
        }
        String userId = userService.saveUser(user);
        return success(userId);
    }

    @Log(module = "用户管理", description = "删除用户")
    @DeleteMapping("/delete")
    public CommonResult<Object> deleteUsers(@RequestBody CommonParamVo deleteUserVo) {
        List<String> ids = deleteUserVo.getIds();
        if (CollectionUtils.isNotEmpty(ids) && ids.contains("1")) {
            return fail(ResultEnum.ADMIN_INFO_DELETED);
        }
        userService.deleteUser(deleteUserVo);
        return success("delete success.");
    }

    @Log(module = "用户管理", description = "更新用户")
    @PutMapping("/update")
    public CommonResult<Object> updateUser(@RequestBody UserVo user) {
        userService.updateUser(user);
        return success("update success.");
    }

    @Log(module = "用户管理", description = "用户列表")
    @PostMapping("/list")
    public CommonResult<Object> getUsers(@RequestBody QueryUserVo user){
        List<User> userList = userService.findUserList(user);
        return success(userList);
    }

    @Log(module = "用户管理", description = "用户详情")
    @GetMapping("/{id}")
    public CommonResult<Object> getUserById(@PathVariable("id") String id) {
        log.info("getUser param is {}", id);
        User user = userService.getUserInfo(id);
        return success(user);
    }

    @GetMapping("/userInfo")
    public CommonResult<Object> getUserInfoByToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        String userId = StpUtil.getLoginIdByToken(token).toString();
        CommonResult<Object> result = getUserById(userId);
        return result;
    }

    @Log(module = "用户管理", description = "分页查询")
    @PostMapping(value = "/page")
    public CommonResult<Object> getUsersByPage(@RequestBody QueryUserVo queryUserVo){
        log.info("getPage param is {}",queryUserVo);
        PageResult<User> userPage = userService.findUserPage(queryUserVo);
        return success(userPage);
    }

    @Log(module = "用户管理", description = "修改密码")
    @PostMapping(value ="/changePwd")
    public CommonResult<Object> changePassword(String oldPwd, String newPwd, HttpServletRequest request){
        String token = request.getHeader("token");
        if (StringUtils.isNotBlank(oldPwd) && StringUtils.isNotBlank(newPwd)){
            userService.updatePassword(oldPwd, newPwd, token);
            return success("update password success.");
        }
        return fail(ResultEnum.UPDATE_PWD_FAILED);
    }

}
