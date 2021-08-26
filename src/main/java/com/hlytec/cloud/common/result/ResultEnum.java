package com.hlytec.cloud.common.result;

/**
 * @description: CommonResultEnum
 * @author: zero
 * @date: 2021/4/15 11:41
 */
public enum ResultEnum {

    /*=======================通用错误码====================*/
    SUCCESS("0", "成功！"),
    FAIL("1", "失败"),
    PARAM_INVALIDATE("2","参数异常！"),
    PHOTO_FORMAT_ERROR("3","图片格式错误！"),
    /*=======================登录相关101====================*/
    TOKEN_INVALIDATE("101001","token无效！"),
    TOKEN_EXPIRED("101002","token已过期！"),
    VERIFIED_ERROR("101003","验证码错误！"),
    /*=======================用户相关102====================*/
    USER_NOT_EXITS("102001","用户不存在！"),
    USER_LOCKED("102002","用户已锁定！"),
    USER_INFO_INVALIDATE("102003","用户名或密码错误！"),
    ADMIN_INFO_DELETED("102004","超级管理员不能被删除！"),
    UPDATE_PWD_FAILED("102005","修改密码失败！"),
    USER_IS_EXITS("102006","用户已存在！"),
    USER_HAS_LOGIN("102007","该用户在其他地方登录！"),
    PASSWORD_IS_SAME("102008","新旧密码相同！"),
    /*=======================角色相关104====================*/
    ROLE_IS_EXITS("104001","角色已存在！"),
    ROLE_BIND_USER("104002","角色已被用户绑定，不能删除！"),
    /*=======================站点相关106====================*/
    NO_DATA_ACCESS_AUTH("106001", "没有数据访问权限！"),
    STATION_CANNOT_DEL("106002", "站点下存在设备，不能删除！"),
    /*=======================设备相关110====================*/
    UPDATE_DEVICE_FAILED("110001","更新设备及电池基础信息失败！"),
    DOWNLOAD_EXCEL_FAILED("110002","下载excel模板失败！"),
    BATCH_SAVE_FAILED("110003","批量导入设备失败！"),
    DEVICE_HAS_DISCHARGE("110004","设备已经开始放电！"),
    DEVICE_HAS_CHARGE("110005","设备已经开始充电！"),
    DEVICE_OFFLINE("110006","设备离线或故障！"),
    DEVICE_CARDID_IS_EXITS("110007","设备编号已存在！"),
    /*=======================运维u相关110====================*/
    INVOKE_TARGET_INVALIDATE("109001","调用目标字符串异常！"),
    ;

    private final String code;
    private final String msg;

    ResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
