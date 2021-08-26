package com.hlytec.cloud.biz.knowledge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hlytec.cloud.biz.knowledge.model.entity.KnowledgeRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @description: KnowledgeMapper
 * @author: JackChen
 * @date: 2021/5/26 13:57
 */
@Mapper
public interface KnowledgeMapper extends BaseMapper<KnowledgeRepository> {

    void updateCount(@Param("id") String id, @Param("reviewCount") int reviewCount);
}
