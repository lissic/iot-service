package com.hlytec.cloud.biz.system.user.model.vo;

import java.util.Collections;
import java.util.List;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @description: UserVo
 * @author: zero
 * @date: 2021/6/3 9:58
 */
@Data
public class UserVo {

    private String id;

    @NotEmpty(message = "昵称不能为空")
    private String nickName;

    @NotEmpty(message = "登录名不能为空")
    private String loginName;

    @NotEmpty(message = "密码不能为空")
    private String password;

    @NotEmpty(message = "所属区域不能为空")
    private String area;

    private Integer sex = 1;

    private String email;

    private String phone;

    private String deptId;

    private List<String> roles = Collections.emptyList();

    private String introduction;

    private String photo;
}
