package com.hlytec.cloud.biz.knowledge.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlytec.cloud.biz.knowledge.model.vo.QueryKnowledgeVo;
import com.hlytec.cloud.common.entity.CommonParamVo;
import com.hlytec.cloud.common.result.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hlytec.cloud.biz.knowledge.mapper.KnowledgeMapper;
import com.hlytec.cloud.biz.knowledge.model.entity.KnowledgeRepository;
import com.hlytec.cloud.common.service.BaseService;

/**
 * @description: KnowledgeService
 * @author: JackChen
 * @date: 2021/5/26 14:02
 */
@Service
@Transactional(readOnly = false)
public class KnowledgeService extends BaseService<KnowledgeMapper, KnowledgeRepository> {
    @Autowired
    private KnowledgeMapper knowledgeMapper;

    public List<KnowledgeRepository> findDocumentByKeywords(String keywords){
        return knowledgeMapper.selectList(new QueryWrapper<KnowledgeRepository>().like("keywords",keywords));
    }

    public void updateReviewCount(QueryKnowledgeVo queryKnowledgeVo){
        knowledgeMapper.updateCount(queryKnowledgeVo.getId(),queryKnowledgeVo.getReviewCount());
    }

    public PageResult<KnowledgeRepository> findKnowledgePage(QueryKnowledgeVo queryKnowledgeVo){
        Page<KnowledgeRepository> page = new Page<>(queryKnowledgeVo.getPageNo(),queryKnowledgeVo.getPageSize());
        QueryWrapper<KnowledgeRepository> query = buildQueryParam(
                queryKnowledgeVo.getTitle(),
                queryKnowledgeVo.getKeywords(),
                queryKnowledgeVo.getCategory(),
                queryKnowledgeVo.getStatus(),
                queryKnowledgeVo.getCreateUser()
        );
        query.orderByDesc("create_time");
        Page<KnowledgeRepository> page1 = knowledgeMapper.selectPage(page,query);
        PageResult<KnowledgeRepository> pageResult = new PageResult<>(page1);
        return pageResult;
    }

    private QueryWrapper<KnowledgeRepository> buildQueryParam(String title,String keywords,Integer category,Integer status,String createUser){
        QueryWrapper<KnowledgeRepository> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(title)) {
            queryWrapper.like("title", title);
        }
        if (StringUtils.isNotEmpty(keywords)) {
            queryWrapper.like("keywords", keywords);
        }
        if (category != null) {
            queryWrapper.eq("category", category);
        }
        if (status != null){
            queryWrapper.eq("status",status);
        }
        if (StringUtils.isNotEmpty(createUser)){
            queryWrapper.like("create_user",createUser);
        }
        return queryWrapper;
    }
    public void deleteKnowledge(CommonParamVo deleteKnowledgeVo){
        List<String> knowledgeIds = deleteKnowledgeVo.getIds();
        this.batchDelete(knowledgeIds);
    }
}
