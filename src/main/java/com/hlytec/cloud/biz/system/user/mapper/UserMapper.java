package com.hlytec.cloud.biz.system.user.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlytec.cloud.biz.system.user.model.entity.User;
import com.hlytec.cloud.biz.system.user.model.entity.UserRole;
import com.hlytec.cloud.biz.system.user.model.vo.QueryUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @description: UserMapper
 * @author: zero
 * @date: 2021/4/16 10:10
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据登录名获取用户
     * @param loginName loginName
     * @return User
     */
    User getUserByLoginName(String loginName);

    /**
     * 保存用户角色关联
     *
     * @param userRoleList  userRoleList
     */
    void saveUserRole(List<UserRole> userRoleList);

    /**
     * 删除用户角色关联
     *
     * @param userIds userId
     */
    void deleteUserRole(List<String> userIds);

    /**
     * 更新用户角色关联
     *
     * @param userId  userId
     * @param roleIds roleIds
     */
    void updateUserRole(String userId, String roleIds);

    /**
     * 根据用户获取关联角色
     *
     * @param userId userId
     * @return String
     */
    List<String> getRoleByUser(String userId);

    /**
     * 查询用户分页信息
     * @param queryUserVo 查询参数
     * @return Page<User>
     */
    List<User> findUserList(@Param("queryUserVo") QueryUserVo queryUserVo);
}
