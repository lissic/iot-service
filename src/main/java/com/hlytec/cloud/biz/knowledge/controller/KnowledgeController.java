package com.hlytec.cloud.biz.knowledge.controller;

import java.util.List;

import com.hlytec.cloud.biz.knowledge.model.vo.QueryKnowledgeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlytec.cloud.biz.knowledge.model.entity.KnowledgeRepository;
import com.hlytec.cloud.biz.knowledge.service.KnowledgeService;
import com.hlytec.cloud.common.controller.BaseController;
import com.hlytec.cloud.common.entity.CommonParamVo;
import com.hlytec.cloud.common.result.CommonResult;
import com.hlytec.cloud.common.result.PageResult;

/**
 * @description: KnowledgeController
 * @author: JackChen
 * @date: 2021/5/26 14:00
 */
@RestController
@RequestMapping("/net/knowledge")
public class KnowledgeController extends BaseController {

    @Autowired
    private KnowledgeService knowledgeService;

    @GetMapping("/{id}")
    public CommonResult<Object> findDetails(@PathVariable("id") String id){
        KnowledgeRepository knowledgeRepository = knowledgeService.get(id);
        return success(knowledgeRepository);
    }

    @GetMapping("/findAll")
    public PageResult<KnowledgeRepository> findDocumentList(Page page){
        Page<KnowledgeRepository> knowledgePage = new Page<>(page.getCurrent(),page.getSize(),page.getTotal());
        PageResult<KnowledgeRepository> knowledgePageResult = knowledgeService.findPage(knowledgePage,null);
        return knowledgePageResult;
    }

    @PostMapping("/page")
    public CommonResult<Object> findPage(@RequestBody QueryKnowledgeVo queryKnowledgeVo){
        PageResult<KnowledgeRepository> pageResult = knowledgeService.findKnowledgePage(queryKnowledgeVo);
        return success(pageResult);
    }

    @PostMapping("/find/{keywords}")
    public CommonResult<Object> findDocumentByKeywords(@PathVariable("keywords") String keywords){
        List<KnowledgeRepository> knowledgeList = knowledgeService.findDocumentByKeywords(keywords);
        return success(knowledgeList);
    }

    @PutMapping("/modify")
    public CommonResult<Object> modifyDocument(@RequestBody @Validated KnowledgeRepository knowledgeRepository){
        knowledgeService.updateById(knowledgeRepository);
        return success();
    }

    @PutMapping("/updateCount")
    public CommonResult<Object> updateCount(@RequestBody QueryKnowledgeVo queryKnowledgeVo){
        knowledgeService.updateReviewCount(queryKnowledgeVo);
        return success();
    }

    @PostMapping("/add")
    public CommonResult<Object> addDocument(@RequestBody @Validated KnowledgeRepository knowledgeRepository){
        knowledgeService.save(knowledgeRepository);
        return success();
    }

    @DeleteMapping("/delete/{id}")
    public CommonResult<Object> deleteDocument(@PathVariable("id") String id){
        knowledgeService.delete(id);
        return success();
    }

    @DeleteMapping("/delete")
    public CommonResult<Object> deleteDocuments(@RequestBody CommonParamVo deleteKnowledgeVo){
        knowledgeService.deleteKnowledge(deleteKnowledgeVo);
        return success("delete success");
    }
}
