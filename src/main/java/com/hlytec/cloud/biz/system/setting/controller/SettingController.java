package com.hlytec.cloud.biz.system.setting.controller;

import com.hlytec.cloud.biz.system.setting.model.entity.Setting;
import com.hlytec.cloud.biz.system.setting.model.vo.QuerySettingVo;
import com.hlytec.cloud.biz.system.setting.service.SettingService;
import com.hlytec.cloud.common.controller.BaseController;
import com.hlytec.cloud.common.result.CommonResult;
import com.hlytec.cloud.common.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @description: SettingController
 * @author: JackChen
 * @date: 2021/6/11 16:33
 */
@RestController
@RequestMapping("/sys/setting")
public class SettingController extends BaseController {
    @Autowired
    private SettingService settingService;

    @PostMapping("/save")
    public CommonResult<Object> saveSetting(@RequestBody @Validated Setting setting){
        settingService.save(setting);
        return success("save success");
    }

    @GetMapping("/find/{id}")
    public CommonResult<Object> findSetting(@PathVariable("id") String id){
       Setting setting = settingService.get(id);
        return success(setting);
    }

    @PostMapping("/page")
    public CommonResult<Object> findPage(@RequestBody QuerySettingVo querySettingVo) {
        PageResult<Setting> result = settingService.findSettingPage(querySettingVo);
        return success(result);
    }
}
