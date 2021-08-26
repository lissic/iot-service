package com.hlytec.cloud.biz.system.log.annotation;

import java.lang.annotation.*;

/**
 * @description: 日志自定义注解
 * @author: zero
 * @date: 2021/4/28 9:24
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 模块名
     *
     * @return String
     */
    String module() default "";

    /**
     * 描述
     *
     * @return String
     */
    String description() default "";
}
