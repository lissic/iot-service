package com.hlytec.cloud.biz.knowledge.model.vo;

import com.hlytec.cloud.common.entity.BaseQueryEntity;
import lombok.Data;

import java.util.Date;

/**
 * @description: QueryKnowledgeVo
 * @author: JackChen
 * @date: 2021/7/1 10:50
 */
@Data
public class QueryKnowledgeVo extends BaseQueryEntity {

    private String id;

    private String title;

    private String synopsis;

    private String keywords;

    private Integer category;
    /**
     *  0-隐藏，1-显示
      */
    private Integer status;

    private Integer reviewCount;

    private String content;

    private String attach;

    private String createUser;

    private Date createTime;

    private String remarks;
}
