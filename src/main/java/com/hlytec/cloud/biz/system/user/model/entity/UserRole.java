package com.hlytec.cloud.biz.system.user.model.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @description: UserRole
 * @author: zero
 * @date: 2021/6/22 9:13
 */
@Data
@Builder
public class UserRole {
    private String userId;
    private String roleId;
}
