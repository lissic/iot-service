package com.hlytec.cloud.common.constants;

import java.util.Arrays;
import java.util.List;

/**
 * @description: SysLogConstants
 * @author: zero
 * @date: 2021/4/28 15:23
 */
public class SysConstants {
    /**
     * 日志类型（access：接入日志；update：修改日志；select：查询日志；loginLogout：登录登出；）
     */
    public static final String TYPE_ACCESS = "access";
    public static final String TYPE_UPDATE = "update";
    public static final String TYPE_SELECT = "select";
    public static final String TYPE_LOGIN_LOGOUT = "loginLogout";
    /**
     * 响应
     */
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";
    public static final int SUCCESS_CODE = 1;
    public static final int FAIL_CODE = 0;
    /**
     * admin id
     */
    public static final String ADMIN_ID = "1";
    /**
     * 系统告警策略编码
     */
    public static final List<String> SYSTEM_CODE = Arrays.asList("3001", "3002", "3003");

    /**
     * 设备Guid前缀
     */
    public static final String GUID_PREFIX = "A0000000000000000";

    /*============================收到消息后发送响应的topic============================*/

}
