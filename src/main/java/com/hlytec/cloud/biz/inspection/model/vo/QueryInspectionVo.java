package com.hlytec.cloud.biz.inspection.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hlytec.cloud.common.entity.BaseQueryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @description: QueryInspectionVo
 * @author: zero
 * @date: 2021/7/22 14:25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryInspectionVo extends BaseQueryEntity {
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    private Integer status;
    private String checkPerson;
}
