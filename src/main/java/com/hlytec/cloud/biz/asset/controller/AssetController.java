package com.hlytec.cloud.biz.asset.controller;

import com.hlytec.cloud.biz.asset.model.entity.Asset;
import com.hlytec.cloud.biz.asset.model.vo.QueryAssetVo;
import com.hlytec.cloud.biz.asset.service.AssetService;
import com.hlytec.cloud.common.controller.BaseController;
import com.hlytec.cloud.common.entity.CommonParamVo;
import com.hlytec.cloud.common.result.CommonResult;
import com.hlytec.cloud.common.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @description: AssetController
 * @author: JackChen
 * @date: 2021/5/26 10:03
 */
@RestController
@RequestMapping("/net/assets")
public class AssetController extends BaseController {

    @Autowired
    private AssetService assetService;

    @GetMapping("/findDetails/{id}")
    public CommonResult<Object> findDetails(@PathVariable("id") String id){
        Asset asset = assetService.get(id);
        return success(asset);
    }

    @PutMapping("/modify")
    public CommonResult<Object> modifyAsset(@RequestBody @Validated Asset asset) {
        assetService.updateById(asset);
        return success("modify success");
    }

    @PostMapping("/add")
    public CommonResult<Object> addAsset(@RequestBody @Validated Asset asset) {
        assetService.save(asset);
        return success("add success");
    }

    @PostMapping("/list")
    public CommonResult<Object> findList(@RequestBody Asset asset){
        List<Asset> assetList = assetService.findAssetList(asset);
        return success(assetList);
    }

    @PostMapping("/page")
    public CommonResult<Object> findPage(@RequestBody QueryAssetVo queryAssetVo){
        PageResult<Asset> assetPageResult = assetService.findAssetPage(queryAssetVo);
        return success(assetPageResult);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResult<Object> deleteAsset(@PathVariable("id") String id) {
        assetService.delete(id);
        return success("delete success");
    }

    @DeleteMapping("/delete")
    public CommonResult<Object>deleteAssets(@RequestBody CommonParamVo deleteAssetVo){
        assetService.deleteAsset(deleteAssetVo);
        return success("delete success");
    }

    @PostMapping("/updateStatus")
    public CommonResult<Object> updateStatus(@RequestParam("status") @Validated int status,@RequestParam("id") String id) {
        assetService.updateStatus(status,id);
        return success("update success");
    }

    @PostMapping("/batchExport")
    public CommonResult<Object> batchExport(@RequestBody(required = false) CommonParamVo commonParamVo){
        List<Asset> assetList = assetService.selectAsset(commonParamVo);
        return success(assetList);
    }
}

