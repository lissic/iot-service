package com.hlytec.cloud.biz.system.role.model.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @description: RoleMenu
 * @author: zero
 * @date: 2021/6/22 9:21
 */
@Data
@Builder
public class RoleMenu {
    private String roleId;
    private String menuId;
}
