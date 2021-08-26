package com.hlytec.cloud.biz.system.setting.model.vo;

import lombok.Data;

/**
 * @description: QuerySettingVo
 * @author: JackChen
 * @date: 2021/6/16 9:12
 */
@Data
public class QuerySettingVo {
    private String settingOption;
    private String settingContent;
    private Integer pageNo = 1;
    private Integer pageSize =20;
}
