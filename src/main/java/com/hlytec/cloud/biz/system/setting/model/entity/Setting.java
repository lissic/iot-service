package com.hlytec.cloud.biz.system.setting.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hlytec.cloud.common.entity.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

/**
 * @description: Setting
 * @author: JackChen
 * @date: 2021/6/11 16:38
 */
@Data
@Getter
@Setter
@SuperBuilder
@TableName("sys_setting")
public class Setting extends BaseEntity<Setting> {

    public Setting() {
    }

    /**
     * 设置项
     */
    @NotEmpty(message = "设置项不能为空")
    @TableField("set_option")
    private String settingOption;

    /**
     * 设置内容
     */
    @NotEmpty(message = "设置内容不能为空")
    @TableField("set_content")
    private String settingContent;
}
