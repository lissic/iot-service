package com.hlytec.cloud.biz.system.user.model.vo;

import lombok.Data;

/**
 * @description: QueryUserVo
 * @author: zero
 * @date: 2021/6/3 15:40
 */
@Data
public class QueryUserVo {
    private String nickName;
    private String loginName;
    private Integer sex;
    private String phone;
    private String deptId;
    private String roleId;
    private Integer status;
    private Integer pageNo = 1;
    private Integer pageSize = 20;

}
