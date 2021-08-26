package com.hlytec.cloud.biz.system.log.model.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hlytec.cloud.common.entity.BaseQueryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: QueryLogVo
 * @author: zero
 * @date: 2021/6/10 9:47
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryLogVo extends BaseQueryEntity {
    private String title;
    private String requestUrl;
    private String bizType;
    private String operator;
    private Boolean isException;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date execStartTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date execEndTime;
}
