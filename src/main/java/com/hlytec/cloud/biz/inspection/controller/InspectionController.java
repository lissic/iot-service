package com.hlytec.cloud.biz.inspection.controller;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.hlytec.cloud.biz.inspection.model.entity.Inspection;
import com.hlytec.cloud.biz.inspection.model.vo.QueryInspectionVo;
import com.hlytec.cloud.biz.inspection.service.InspectionService;
import com.hlytec.cloud.biz.system.log.annotation.Log;
import com.hlytec.cloud.common.controller.BaseController;
import com.hlytec.cloud.common.entity.CommonParamVo;
import com.hlytec.cloud.common.result.CommonResult;
import com.hlytec.cloud.common.result.PageResult;
import com.hlytec.cloud.common.result.ResultEnum;

/**
 * @description: InspectionController
 * @author: zero
 * @date: 2021/6/30 17:37
 */
@RestController
@RequestMapping("/net/inspection")
public class InspectionController extends BaseController {

    @Autowired
    private InspectionService inspectionService;

    @Log(module = "设备巡检", description = "新增")
    @PostMapping("/save")
    public CommonResult<Object> saveMenu(@RequestBody @Validated Inspection inspection) {
        String menuId = inspectionService.save(inspection);
        return success(menuId);
    }

    @Log(module = "设备巡检", description = "删除")
    @DeleteMapping("/delete")
    public CommonResult<Object> deleteMenu(@RequestBody CommonParamVo deleteIds) {
        inspectionService.batchDelete(deleteIds.getIds());
        return success();
    }

    @Log(module = "设备巡检", description = "更新")
    @PutMapping("/update")
    public CommonResult<Object> updateMenu(@RequestBody @Validated Inspection inspection) {
        inspectionService.updateById(inspection);
        return success();
    }

    @GetMapping("/{inspectionId}")
    public CommonResult<Object> findDetails(@PathVariable("inspectionId") String inspectionId) {
        Inspection inspection = inspectionService.get(inspectionId);
        return success(inspection);
    }

    @PostMapping("/page")
    public CommonResult<Object> page(@RequestBody QueryInspectionVo inspectionVo) {
        PageResult<Inspection> result = inspectionService.findInspectionPage(inspectionVo);
        return success(result);
    }

    @PostMapping("/list")
    public CommonResult<Object> list(@RequestBody QueryInspectionVo inspectionVo) {
        List<Inspection> result = inspectionService.findInspectionList(inspectionVo);
        return success(result);
    }

    @Log(module = "设备巡检", description = "开始巡检")
    @PutMapping("/startInspection/{inspectionId}")
    public CommonResult<Object> startInspection(@PathVariable("inspectionId") String inspectionId) {
        Inspection param = Inspection.builder().id(inspectionId).realInspectTime(new Date()).status(1).build();
        inspectionService.updateById(param);
        return success();
    }

    @Log(module = "设备巡检", description = "保存结果")
    @PostMapping("/saveResult")
    public CommonResult<Object> saveResult(@RequestBody Inspection inspection) {
        if (Objects.nonNull(inspection)) {
            inspection.setStatus(2);
            inspectionService.updateById(inspection);
            return success();
        } else {
            return fail(ResultEnum.PARAM_INVALIDATE);
        }
    }

}
