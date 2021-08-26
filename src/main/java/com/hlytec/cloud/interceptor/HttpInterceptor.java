package com.hlytec.cloud.interceptor;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hlytec.cloud.common.result.ResultEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.hlytec.cloud.biz.system.user.model.entity.User;
import com.hlytec.cloud.biz.system.user.service.UserService;
import com.hlytec.cloud.common.exception.NetServiceException;
import com.hlytec.cloud.utils.IpUtil;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: HttpInterceptor
 * @author: zero
 * @date: 2021/5/25 9:49
 */

@Slf4j
public class HttpInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        String ipAddr = IpUtil.getIpAddr(request);
        if (StringUtils.isEmpty(token)) {
            throw new NetServiceException(ResultEnum.TOKEN_INVALIDATE);
        } else {
            String loginDevice = StpUtil.getLoginDevice();
            Object loginId = StpUtil.getLoginIdByToken(token);
            User user = userService.get(loginId.toString());
            String cacheToken = StpUtil.getTokenValue();
            // TODO 同一用户只能在一台设备上登录
            if (Objects.equals(token, cacheToken) && Objects.nonNull(user)) {
                return true;
            }
            throw new NetServiceException(ResultEnum.USER_HAS_LOGIN);
        }
    }
}
