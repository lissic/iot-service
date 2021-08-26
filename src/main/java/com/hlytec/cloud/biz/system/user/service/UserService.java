package com.hlytec.cloud.biz.system.user.service;

import java.util.*;
import java.util.stream.Collectors;

import com.hlytec.cloud.common.entity.CommonParamVo;
import com.hlytec.cloud.common.result.ResultEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hlytec.cloud.biz.system.role.model.entity.Role;
import com.hlytec.cloud.biz.system.role.service.RoleService;
import com.hlytec.cloud.biz.system.user.mapper.UserMapper;
import com.hlytec.cloud.biz.system.user.model.entity.User;
import com.hlytec.cloud.biz.system.user.model.entity.UserRole;
import com.hlytec.cloud.biz.system.user.model.vo.QueryUserVo;
import com.hlytec.cloud.biz.system.user.model.vo.UserVo;
import com.hlytec.cloud.biz.system.user.utils.UserUtil;
import com.hlytec.cloud.common.exception.NetServiceException;
import com.hlytec.cloud.common.result.PageResult;
import com.hlytec.cloud.common.service.BaseService;
import com.hlytec.cloud.utils.CryptoUtil;

/**
 * @description: UserService
 * @author: zero
 * @date: 2021/4/16 10:41
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class UserService extends BaseService<UserMapper, User> {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;

    @Transactional(readOnly = false)
    public String saveUser(UserVo userVo) {
        User exitUser = getUserByLoginName(userVo.getLoginName());
        if (Objects.nonNull(exitUser)) {
            throw new NetServiceException(ResultEnum.USER_IS_EXITS);
        }
        String encryptPwd = CryptoUtil.encryptPwd(userVo.getPassword());
        userVo.setPassword(encryptPwd);
        User user = User.builder().build();
        BeanUtils.copyProperties(userVo, user, "roles");
        this.save(user);
        // 保存用户角色关联关系
        saveUserRoles(userVo, user.getId());
        return user.getId();
    }

    private void saveUserRoles(UserVo userVo, String userId) {
        List<UserRole> userRoleList = new ArrayList<>(userVo.getRoles().size());
        List<String> roles = userVo.getRoles();
        if (CollectionUtils.isNotEmpty(roles)) {
            roles.forEach(roleId -> {
                UserRole userRole = UserRole.builder().userId(userId).roleId(roleId).build();
                userRoleList.add(userRole);
            });
            userMapper.saveUserRole(userRoleList);
        }
    }

    @Transactional(readOnly = false)
    public void deleteUser(CommonParamVo deleteUserVo) {
        List<String> userIds = deleteUserVo.getIds();
        this.batchDelete(userIds);
        userMapper.deleteUserRole(userIds);
    }

    @Transactional(readOnly = false)
    public void updateUser(UserVo userVo) {
        User user = User.builder().build();
        BeanUtils.copyProperties(userVo, user, "roles");
        userMapper.update(user, new UpdateWrapper<User>().eq("id", user.getId()));
        updateUserRole(userVo);
    }

    private void updateUserRole(UserVo userVo) {
        List<String> roleIds = userVo.getRoles();
        List<UserRole> userRoleList = new ArrayList<>(roleIds.size());
        if (CollectionUtils.isNotEmpty(roleIds)) {
            userMapper.deleteUserRole(Collections.singletonList(userVo.getId()));
            roleIds.forEach(roleId -> {
                UserRole build = UserRole.builder().userId(userVo.getId()).roleId(roleId).build();
                userRoleList.add(build);
            });
            userMapper.saveUserRole(userRoleList);
        }
    }

    public User getUserInfo(String userId) {
        User user = this.get(userId);
        List<Role> roles = new ArrayList<>();
        List<String> roleIds = userMapper.getRoleByUser(userId);
        if (CollectionUtils.isNotEmpty(roleIds)) {
            roleIds.stream().parallel().forEach(roleId -> {
                Role role = roleService.getRole(roleId);
                roles.add(role);
            });
        }
        user.setRoles(roles);
        return user;
    }

    @Transactional(readOnly = false)
    public void updatePassword(String oldPwd, String newPwd, String token) {
        User userCache = UserUtil.getUserCache(token);
        if (UserUtil.validatePassword(newPwd, userCache.getPassword())) {
            throw new NetServiceException(ResultEnum.PASSWORD_IS_SAME);
        }
        if (UserUtil.validatePassword(oldPwd, userCache.getPassword())){
            String encryptPwd = CryptoUtil.encryptPwd(newPwd);
            userCache.setPassword(encryptPwd);
            UserUtil.refreshCache(userCache, token);
        } else {
            throw new NetServiceException(ResultEnum.UPDATE_PWD_FAILED);
        }
        User user = User.builder().id(userCache.getId()).password(userCache.getPassword()).build();
        userMapper.updateById(user);
    }

    public User getUserByLoginName(String loginName) {
        User user = userMapper.getUserByLoginName(loginName);
        return user;
    }

    public List<User> findUserList(QueryUserVo user) {
        List<User> users = userMapper.findUserList(user);
        return users;
    }

    public PageResult<User> findUserPage(QueryUserVo queryUserVo) {
        List<User> userList = userMapper.findUserList(queryUserVo);
        // 重新计算分页
        int total = userList.size();
        int current = queryUserVo.getPageNo();
        int size = queryUserVo.getPageSize();
        List<User> collect = userList.stream().skip((current - 1) * size).limit(size).collect(Collectors.toList());
        PageResult<User> result = new PageResult<>(current, size, total);
        result.setList(collect);
        return result;
    }

}
