package com.qcz.qmplatform.common.utils;

import cn.hutool.system.HostInfo;
import cn.hutool.system.SystemUtil;
import com.qcz.qmplatform.module.operation.pojo.Computer;
import com.qcz.qmplatform.module.operation.pojo.Cpu;
import com.qcz.qmplatform.module.operation.pojo.Disk;
import com.qcz.qmplatform.module.operation.pojo.Mem;
import com.qcz.qmplatform.module.operation.pojo.ServerInfo;
import com.qcz.qmplatform.module.operation.pojo.SysFile;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static oshi.hardware.CentralProcessor.TickType;

public class SystemUtils extends SystemUtil {

    private static final SystemInfo si = new SystemInfo();
    private static final HardwareAbstractionLayer hal = si.getHardware();

    private static final int OSHI_WAIT_SECOND = 1000;

    private SystemUtils() {

    }

    public static ServerInfo getServerInfo() {
        ServerInfo serverInfo = new ServerInfo();

        serverInfo.setComputer(getComputer());
        serverInfo.setCpu(getCpu());
        serverInfo.setMem(getMem());
        serverInfo.setDisk(getDisk());

        return serverInfo;
    }

    /**
     * 内存信息
     */
    public static Mem getMem() {
        GlobalMemory memory = hal.getMemory();
        Mem mem = new Mem();
        long total = memory.getTotal();
        long available = memory.getAvailable();
        long used = total - available;
        mem.setTotal(convertFileSize(total));
        mem.setUsed(convertFileSize(used));
        mem.setFree(convertFileSize(available));
        mem.setUsage(Arith.mul(Arith.div(used, total, 4), 100));
        return mem;
    }

    /**
     * 磁盘相关信息
     */
    public static Disk getDisk() {
        Disk disk = new Disk();
        long totalDisk = 0, freeDisk = 0, usedDisk = 0;
        List<SysFile> sysFiles = new ArrayList<>();
        FileSystem fileSystem = si.getOperatingSystem().getFileSystem();
        OSFileStore[] fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;
            totalDisk += total;
            freeDisk += free;
            usedDisk += used;
            SysFile sysFile = new SysFile();
            sysFile.setDirName(fs.getMount());
            sysFile.setSysTypeName(fs.getType());
            sysFile.setTypeName(fs.getName());
            sysFile.setTotal(convertFileSize(total));
            sysFile.setFree(convertFileSize(free));
            sysFile.setUsed(convertFileSize(used));
            sysFile.setUsage(Arith.mul(Arith.div(used, total, 4), 100));
            sysFiles.add(sysFile);
        }
        disk.setSysFiles(sysFiles);
        disk.setTotal(convertFileSize(totalDisk));
        disk.setFree(convertFileSize(freeDisk));
        disk.setUsed(convertFileSize(usedDisk));
        disk.setUsage(Arith.mul(Arith.div(usedDisk, totalDisk, 4), 100));
        return disk;
    }

    /**
     * CPU相关信息
     */
    public static Cpu getCpu() {
        // CPU信息
        CentralProcessor processor = hal.getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(OSHI_WAIT_SECOND);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
        long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
        long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
        long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
        long cSys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
        long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
        long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
        long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        Cpu cpu = new Cpu();
        cpu.setCpuNum(processor.getLogicalProcessorCount());
        cpu.setTotal(totalCpu);
        cpu.setSys(cSys);
        cpu.setUsed(user);
        cpu.setWait(iowait);
        cpu.setFree(idle);
        return cpu;
    }

    /**
     * 服务器相关信息
     */
    public static Computer getComputer() {
        Properties props = System.getProperties();
        HostInfo hostInfo = getHostInfo();
        Computer computer = new Computer();
        computer.setComputerName(hostInfo.getName());
        computer.setComputerIp(hostInfo.getAddress());
        computer.setOsName(props.getProperty("os.name"));
        computer.setOsArch(props.getProperty("os.arch"));
        computer.setUserDir(props.getProperty("user.dir"));
        return computer;
    }

    /**
     * 字节转换
     *
     * @param size 字节大小
     * @return 转换后值
     */
    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }
}
