package com.hlytec.cloud.biz.asset.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlytec.cloud.biz.asset.mapper.AssetMapper;
import com.hlytec.cloud.biz.asset.model.entity.Asset;
import com.hlytec.cloud.biz.asset.model.vo.QueryAssetVo;
import com.hlytec.cloud.common.entity.CommonParamVo;
import com.hlytec.cloud.common.result.PageResult;
import com.hlytec.cloud.common.service.BaseService;

/**
 * @description: AssetService
 * @author: JackChen
 * @date: 2021/5/26 10:07
 */
@Service
@Transactional(readOnly = true)
public class AssetService extends BaseService<AssetMapper, Asset> {
    @Autowired
    private AssetMapper assetMapper;

    public List<Asset> findAssetList(Asset asset) {
        QueryWrapper<Asset> queryWrapper = buildQueryParam(
                asset.getName(),
                asset.getAssetCode(),
                asset.getBrand(),
                asset.getDepartmentId(),
                asset.getCategory(),
                asset.getDepartmentName(),
                asset.getModel(),
                asset.getStatus(),
                asset.getUsePersonId());
        List<Asset> assetList = assetMapper.selectList(queryWrapper);
        return assetList;
    }

    public PageResult<Asset> findAssetPage(QueryAssetVo queryAssetVo) {
        Page<Asset> page = new Page<>(queryAssetVo.getPageNo(), queryAssetVo.getPageSize());
        QueryWrapper<Asset> query = buildQueryParam(
                queryAssetVo.getName(),
                queryAssetVo.getAssetCode(),
                queryAssetVo.getBrand(),
                queryAssetVo.getDepartmentId(),
                queryAssetVo.getCategory(),
                queryAssetVo.getDepartmentName(),
                queryAssetVo.getModel(),
                queryAssetVo.getStatus(),
                queryAssetVo.getUsePersonId()
        );
        Page<Asset> assetPage = assetMapper.selectPage(page, query);
        PageResult<Asset> result = new PageResult<>(assetPage);
        return result;
    }

    private QueryWrapper<Asset> buildQueryParam(
            String name, String assetCode, String brand, String departmentId, Integer category, String departmentName, String model, Integer status, String usePersonId) {
        QueryWrapper<Asset> query = new QueryWrapper<>();
        query.orderByDesc("procurement_time");
        if (StringUtils.isNotEmpty(name)) {
            query.like("name", name);
        }
        if (StringUtils.isNotEmpty(assetCode)) {
            query.eq("asset_code", assetCode);
        }
        if (StringUtils.isNotEmpty(brand)) {
            query.like("brand", brand);
        }
        if (StringUtils.isNotEmpty(departmentId)) {
            query.eq("department_id", departmentId);
        }
        if (category != null) {
            query.eq("category", category);
        }
        if (StringUtils.isNotEmpty(departmentName)) {
            query.eq("department_name", departmentName);
        }
        if (StringUtils.isNotEmpty(model)) {
            query.eq("model", model);
        }
        if (status != null) {
            query.eq("status", status);
        }
        if (StringUtils.isNotEmpty(usePersonId)) {
            query.eq("use_person_id", usePersonId);
        }
        return query;
    }

    /**
     * 更新资产状态
     *
     * @param status status
     * @param id     id
     */
    @Transactional(readOnly = false)
    public void updateStatus(int status, String id) {
        assetMapper.updateStatus(status, id);
    }

    /**
     * 批量删除
     *
     * @param deleteAssetVo
     */
    @Transactional(readOnly = false)
    public void deleteAsset(CommonParamVo deleteAssetVo) {
        List<String> assetIds = deleteAssetVo.getIds();
        this.batchDelete(assetIds);
    }

    /**
     * 批量查询
     *
     * @param selectByIdsVo selectByIdsVo
     * @return
     */
    public List<Asset> selectAsset(CommonParamVo selectByIdsVo) {
        List<Asset> batchIds= assetMapper.selectBatchIds(selectByIdsVo.getIds());
        return batchIds;
    }

}
