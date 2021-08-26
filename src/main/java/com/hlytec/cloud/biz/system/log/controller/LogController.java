package com.hlytec.cloud.biz.system.log.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hlytec.cloud.biz.system.log.model.entity.SysLog;
import com.hlytec.cloud.biz.system.log.model.vo.QueryLogVo;
import com.hlytec.cloud.biz.system.log.service.LogService;
import com.hlytec.cloud.common.controller.BaseController;
import com.hlytec.cloud.common.result.CommonResult;
import com.hlytec.cloud.common.result.PageResult;

/**
 * @description: LogController
 * @author: zero
 * @date: 2021/4/28 10:06
 */
@RestController
@RequestMapping("/sys/log")
public class LogController extends BaseController {

    @Autowired
    private LogService logService;

    @PostMapping("/page")
    public CommonResult<Object> queryLog(@RequestBody(required = false) QueryLogVo queryLogVo) {
        PageResult<SysLog> result = logService.queryLogPage(queryLogVo);
        return success(result);
    }
}
