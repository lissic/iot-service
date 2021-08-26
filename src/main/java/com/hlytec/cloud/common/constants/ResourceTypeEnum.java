package com.hlytec.cloud.common.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @description: PermissionEnum
 * @author: zero
 * @date: 2021/4/30 15:11
 */
public enum ResourceTypeEnum {
    /**
     * 菜单
     */
    MENU("menu"),

    /**
     * 按钮
     */
    BUTTON("button");

    @EnumValue
    private final String type;

    ResourceTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
