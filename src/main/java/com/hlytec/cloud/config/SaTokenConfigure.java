package com.hlytec.cloud.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.router.SaRouterUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.hlytec.cloud.common.result.CommonResult;
import com.hlytec.cloud.common.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: SaTokenConfigure
 * @author: zero
 * @date: 2021/5/24 18:20
 */
@Configuration
@Slf4j
public class SaTokenConfigure {
    /**
     * 注册
     */
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()
                .addInclude("/**").addExclude("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**"
                        ,"/doc.html/**","/error","/favicon.ico", "/captcha")
                .setAuth(r -> {
                    SaRouterUtil.match("/**", "/login", StpUtil::checkLogin);
                })
                .setError(e -> JSONUtil.toJsonStr(new CommonResult<>(ResultEnum.TOKEN_INVALIDATE, null)))
                .setBeforeAuth(r -> {
                    SaHolder.getResponse()
                            // 服务器名称
                            .setServer("net-server")
                            // 是否可以在iframe显示视图： DENY=不可以 | SAMEORIGIN=同域下可以 | ALLOW-FROM uri=指定域名下可以
                            .setHeader("X-Frame-Options", "SAMEORIGIN")
                            // 是否启用浏览器默认XSS防护： 0=禁用 | 1=启用 | 1; mode=block 启用, 并在检查到XSS攻击时，停止渲染页面
                            .setHeader("X-Frame-Options", "1; mode=block")
                            // 禁用浏览器内容嗅探
                            .setHeader("X-Content-Type-Options", "nosniff")
                    ;
                });
    }
}
