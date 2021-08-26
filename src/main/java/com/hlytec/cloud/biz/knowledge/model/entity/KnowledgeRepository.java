package com.hlytec.cloud.biz.knowledge.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hlytec.cloud.common.entity.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

/**
 * @description: KnowledgeRepository
 * @author: JackChen
 * @date: 2021/5/26 13:44
 */
@Data
@Getter
@Setter
@SuperBuilder
@TableName("net_knowledge_repository")
public class KnowledgeRepository extends BaseEntity<KnowledgeRepository> {

    @NotEmpty(message = "标题不能为空")
    @TableField("title")
    private String title;

    @TableField("synopsis")
    private String synopsis;

    @TableField("keywords")
    private String keywords;

    @TableField("category")
    private Integer category;

    @TableField("status")
    private int status;

    @TableField("review_count")
    private int reviewCount;

    @TableField("content")
    private String content;

    @TableField("attach")
    private String attach;

    public KnowledgeRepository() {
    }
}
