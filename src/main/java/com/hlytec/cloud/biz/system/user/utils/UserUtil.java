package com.hlytec.cloud.biz.system.user.utils;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.hlytec.cloud.biz.system.user.model.entity.User;
import com.hlytec.cloud.common.constants.SysConstants;
import com.hlytec.cloud.utils.CryptoUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * @description: UserUtil
 * @author: zero
 * @date: 2021/5/18 9:34
 */
public class UserUtil {

    private static final Logger logger = LoggerFactory.getLogger(UserUtil.class);

    private static final String USER_CACHE = "userCache";

    private UserUtil() {}

    /**
     * 是否为管理员
     *
     * @param id id
     * @return boolean
     */
    public static boolean isAdmin(String id){
        return StringUtils.equals(id, "1");
    }

    /**
     * 获取当前登录用户id
     * @return String
     */
    public static String getCurrentUserId() {
        Object loginId = null;
        try {
            loginId = StpUtil.getLoginId();
        } catch (Exception e) {
            logger.error("获取不到系统用户.");
        }
        // 项目刚起，无用户登录，设置默认用户为admin
        return loginId!=null?loginId.toString(): SysConstants.ADMIN_ID;
    }

    /**
     * 验证密码
     * @param visiblePwd 明文密码
     * @param password 密文密码
     */
    public static boolean  validatePassword(String visiblePwd,String password){
        return CryptoUtil.encryptPwd(visiblePwd).equals(password);
    }

    /**
     * 获取登录用户缓存 session方式
     * @param token token
     * @return User
     */
    public static User getUserCache(String token) {
        SaSession session = StpUtil.getTokenSessionByToken(token);
        User user = (User)session.get(USER_CACHE);
        if (Objects.nonNull(user)) {
            return user;
        }
        return new User();
    }

    public static User getCurrentUser() {
        String token = StpUtil.getTokenValue();
        return getUserCache(token);
    }

    public static void refreshCache(User user, String token) {
        SaSession session = StpUtil.getTokenSessionByToken(token);
        if (Objects.nonNull(user)) {
            session.set(USER_CACHE, user);
        }
    }

}
