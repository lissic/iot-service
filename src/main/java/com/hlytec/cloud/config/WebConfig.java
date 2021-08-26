package com.hlytec.cloud.config;

import com.hlytec.cloud.interceptor.HttpInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description: WebConfig
 * @author: zero
 * @date: 2021/5/25 10:09
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public HttpInterceptor httpInterceptor() {
        return new HttpInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login","/captcha",
                        "/favicon.ico");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                //.allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
    }
}
