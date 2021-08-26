package com.hlytec.cloud.biz.authority.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hlytec.cloud.common.result.ResultEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hlytec.cloud.biz.authority.model.entity.Captcha;
import com.hlytec.cloud.biz.authority.service.CaptchaService;
import com.hlytec.cloud.biz.authority.service.LoginService;
import com.hlytec.cloud.biz.system.log.annotation.Log;
import com.hlytec.cloud.common.controller.BaseController;
import com.hlytec.cloud.common.result.CommonResult;
import com.hlytec.cloud.utils.IpUtil;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: LoginController
 * @author: zero
 * @date: 2021/4/19 15:45
 */
@Slf4j
@RestController
public class LoginController extends BaseController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private CaptchaService captchaService;

    /**
     * 登录接口
     * @param username username
     * @param password password
     * @param verifyCode verifyCode
     * @return CommonResult<Object>
     */
    @Log(module = "权限管理", description = "登录")
    @GetMapping("/login")
    public CommonResult<Object> login(String username, String password, String verifyCode, HttpServletRequest request) {
        // 先校验验证码是否正确
        String captcha = request.getSession().getAttribute("captcha").toString();
        if (StringUtils.equalsIgnoreCase(verifyCode,captcha)) {
            Map<String, String> data = new HashMap<>(1);
            String ipAddr = IpUtil.getIpAddr(request);
            String token = loginService.login(username, password, ipAddr);
            if (StringUtils.isEmpty(token)) {
                return fail(ResultEnum.USER_INFO_INVALIDATE);
            }
            data.put("token", token);
            return success(data);
        } else {
            return fail(ResultEnum.VERIFIED_ERROR);
        }
    }

    @GetMapping("/logout")
    public CommonResult<Object> logout(String username, HttpServletRequest request) {
        String token = request.getHeader("token");
        String ipAddr = IpUtil.getIpAddr(request);
        Object loginId = StpUtil.getLoginIdByToken(token);
        return loginService.logout(loginId.toString(), username, ipAddr);
    }

    @GetMapping("/captcha")
    public CommonResult<Object> getVerifyCode(int width, int height, HttpServletRequest request) {
        Captcha captcha = captchaService.generate(width, height);
        String code = captcha.getCode();
        Base64.Encoder encoder = Base64.getEncoder();
        String image =  encoder.encodeToString(captcha.getImgBytes());
        log.info("获取到的验证码为：{}", code);
        String result = "data:image/png;base64,"+image;
        request.getSession().setAttribute("captcha", code);
        return success(result);
    }
}
