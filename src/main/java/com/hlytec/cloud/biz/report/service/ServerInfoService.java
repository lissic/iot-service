package com.hlytec.cloud.biz.report.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.hlytec.cloud.biz.report.model.po.*;
import com.hlytec.cloud.biz.report.model.vo.ServerInfo;
import com.hlytec.cloud.utils.MathUtil;
import com.hlytec.cloud.utils.ServerUtil;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

/**
 * @description: ServerInfoInfoServiceImpl
 * @author: zero
 * @date: 2021/4/27 10:02
 */
@Service
@Primary
public class ServerInfoService {

    private final SystemInfo si = new SystemInfo();
    private final HardwareAbstractionLayer hal = si.getHardware();

    public ServerInfo overview() {
        return ServerInfo.builder()
                .cpu(getCpuInfo())
                .sys(getSysInfo())
                .mem(getMemInfo())
                .jvm(getJvmInfo())
                .disks(getDisks())
                .build();
    }

    public CpuInfo getCpuInfo() {
        CpuInfo cpu = new CpuInfo();
        CentralProcessor processor = hal.getProcessor();
        // CPU信息
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(1000);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        cpu.setCpuNum(processor.getLogicalProcessorCount());
        cpu.setTotal(totalCpu);
        cpu.setSys(cSys);
        cpu.setUsed(user);
        cpu.setWait(iowait);
        cpu.setFree(idle);
        return cpu;
    }

    public MemInfo getMemInfo() {
        GlobalMemory memory = hal.getMemory();
        MemInfo mem = new MemInfo();
        mem.setTotal(memory.getTotal());
        mem.setUsed(memory.getTotal() - memory.getAvailable());
        mem.setFree(memory.getAvailable());
        return mem;
    }

    public List<DiskInfo> getDisks() {
        List<DiskInfo> disks = new LinkedList<>();
        OperatingSystem os = si.getOperatingSystem();
        FileSystem fileSystem = os.getFileSystem();
        List<OSFileStore> fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;
            DiskInfo sysFile = new DiskInfo();
            sysFile.setDirName(ServerUtil.delimiterConversion(fs.getMount()));
            sysFile.setSysTypeName(fs.getType());
            sysFile.setTypeName(fs.getName());
            sysFile.setTotal(convertFileSize(total));
            sysFile.setFree(convertFileSize(free));
            sysFile.setUsed(convertFileSize(used));
            sysFile.setUsage(MathUtil.mul(MathUtil.div(used, total, 4), 100));
            disks.add(sysFile);
        }
        return disks;
    }

    public JvmInfo getJvmInfo() {
        JvmInfo jvm = new JvmInfo();
        Properties props = System.getProperties();
        jvm.setTotal(Runtime.getRuntime().totalMemory());
        jvm.setMax(Runtime.getRuntime().maxMemory());
        jvm.setFree(Runtime.getRuntime().freeMemory());
        jvm.setVersion(props.getProperty("java.version"));
        jvm.setHome(ServerUtil.delimiterConversion(props.getProperty("java.home")));
        return jvm;
    }

    public SysInfo getSysInfo() {
        SysInfo sys = new SysInfo();
        Properties props = System.getProperties();
        sys.setSysName(ServerUtil.getHostName());
        sys.setSysIp(ServerUtil.getHostIp());
        sys.setOsName(props.getProperty("os.name"));
        sys.setOsArch(props.getProperty("os.arch"));
        return sys;
    }

    /**
     * 字节转换
     *
     * @param size 字节大小
     * @return 转换后值 string
     */
    public String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        // 文件大小
        String fileSize;
        float f = (float) size / mb;
        fileSize = String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        return fileSize;
    }
}
