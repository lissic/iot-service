package com.hlytec.cloud.biz.authority.service;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.hlytec.cloud.biz.system.user.model.entity.User;
import com.hlytec.cloud.biz.system.user.service.UserService;
import com.hlytec.cloud.common.result.CommonResult;
import com.hlytec.cloud.common.result.ResultEnum;
import com.hlytec.cloud.utils.CryptoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @description: LoginService
 * @author: zero
 * @date: 2021/5/25 11:48
 */
@Service
public class LoginService {

    @Autowired
    private UserService userService;

    /**
     * 校验密码，后期可以加上验证码之类的校验
     * @param loginName loginName
     * @param password  password
     * @param ipAddr ipAddr
     * @return String
     */
    public String login(String loginName, String password, String ipAddr) {
        if (StpUtil.isLogin()) {
            String userId = StpUtil.getLoginIdAsString();
            User user = userService.get(userId);
            if (Objects.nonNull(user) && Objects.equals(user.getLoginName(), loginName)) {
                return StpUtil.getTokenValue();
            }
        }
        User user = userService.getUserByLoginName(loginName);
        if (Objects.nonNull(user) && checkPwd(password, user.getPassword())) {
            StpUtil.setLoginId(user.getId(), ipAddr);
            String token = StpUtil.getTokenValue();
            SaSession session = StpUtil.getTokenSessionByToken(token);
            session.set("userCache",user);
            return token;
        }
        return null;
    }

    public CommonResult<Object> logout(String userId, String loginName, String ipAddr) {
        User user = userService.get(userId);
        if (Objects.nonNull(user) && Objects.equals(loginName, user.getLoginName())) {
            StpUtil.logoutByLoginId(userId, ipAddr);
            return new CommonResult<>(ResultEnum.SUCCESS, "logout success.");
        }
        return new CommonResult<>(ResultEnum.FAIL, "logout failed.");
    }

    private boolean checkPwd(String password, String encodePwd) {
        String pwd = CryptoUtil.decryptPwd(encodePwd);
        return Objects.equals(pwd, password);
    }
}
