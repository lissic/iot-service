package com.hlytec.cloud.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hlytec.cloud.biz.asset.model.entity.Asset;
import com.hlytec.cloud.biz.asset.model.vo.QueryAssetVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: AssetMapper
 * @author: JackChen
 * @date: 2021/5/26 10:36
 */
@Mapper
public interface AssetMapper extends BaseMapper<Asset> {

    /**
     * @param status
     * @param id
     */
    void updateStatus(@Param("status") int status, @Param("id") String id);

    List<QueryAssetVo> findAssetPage(QueryAssetVo queryAssetVo);
}
