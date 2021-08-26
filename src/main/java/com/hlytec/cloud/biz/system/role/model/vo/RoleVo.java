package com.hlytec.cloud.biz.system.role.model.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @description: RoleVo
 * @author: zero
 * @date: 2021/5/24 14:13
 */
@Data
public class RoleVo {
    private String id;
    @NotEmpty(message = "角色名不能为空")
    private String role;
    private String description;
    private Boolean available = true;
    private Boolean isAdmin = false;
    private String remarks;
    private List<String> menus = Collections.emptyList();
}
