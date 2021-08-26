package com.hlytec.cloud.biz.system.menu.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @description: MenuTree
 * @author: zero
 * @date: 2021/6/9 9:24
 */
@Data
public class MenuTree {

    private String id;

    private String name;

    private String resourceType;

    private String permission = "*";

    private String url;

    private String parentId;

    private String parentIds;

    private Boolean available;

    private List<MenuTree> subMenu;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
