package com.hlytec.cloud.biz.report.controller;

import java.util.List;

import com.hlytec.cloud.biz.report.service.ServerInfoService;
import com.hlytec.cloud.common.controller.BaseController;
import com.hlytec.cloud.common.result.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hlytec.cloud.biz.system.log.annotation.Log;
import com.hlytec.cloud.biz.report.model.po.*;
import com.hlytec.cloud.biz.report.model.vo.ServerInfo;

/**
 * @description: ServerController
 * @author: zero
 * @date: 2021/4/27 10:31
 */
@RestController
@RequestMapping("/report/server")
public class ServerController extends BaseController {

    @Autowired
    private ServerInfoService serverInfoService;

    @Log(module = "系统监控", description = "信息总览")
    @GetMapping("/overview")
    public CommonResult<Object> getServerInfo() {
        ServerInfo overview = serverInfoService.overview();
        return success(overview);
    }

    @GetMapping("/cpu")
    public CommonResult<Object> getCpuInfo() {
        CpuInfo cpuInfo = serverInfoService.getCpuInfo();
        return success(cpuInfo);
    }

    @GetMapping("/memory")
    public CommonResult<Object> getMemInfo() {
        MemInfo memInfo = serverInfoService.getMemInfo();
        return success(memInfo);
    }

    @GetMapping("/jvm")
    public CommonResult<Object> getJvmInfo() {
        JvmInfo jvmInfo = serverInfoService.getJvmInfo();
        return success(jvmInfo);
    }

    @GetMapping("/system")
    public CommonResult<Object> getSysInfo() {
        SysInfo sysInfo = serverInfoService.getSysInfo();
        return success(sysInfo);
    }

    @GetMapping("/disks")
    public CommonResult<Object> getDisks() {
        List<DiskInfo> disks = serverInfoService.getDisks();
        return success(disks);
    }
}
