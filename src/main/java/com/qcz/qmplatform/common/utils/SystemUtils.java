package com.qcz.qmplatform.common.utils;

import cn.hutool.system.HostInfo;
import cn.hutool.system.OsInfo;
import cn.hutool.system.SystemUtil;
import com.qcz.qmplatform.module.operation.pojo.Computer;
import com.qcz.qmplatform.module.operation.pojo.Cpu;
import com.qcz.qmplatform.module.operation.pojo.Disk;
import com.qcz.qmplatform.module.operation.pojo.Mem;
import com.qcz.qmplatform.module.operation.pojo.ServerInfo;
import com.qcz.qmplatform.module.operation.pojo.SysFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import static oshi.hardware.CentralProcessor.TickType;

public class SystemUtils extends SystemUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemUtils.class);

    private static final SystemInfo SI = new SystemInfo();
    private static final HardwareAbstractionLayer LAYER = SI.getHardware();
    private static final OperatingSystem OPERATING_SYSTEM = SI.getOperatingSystem();
    private static final HardwareAbstractionLayer HARDWARE = SI.getHardware();

    private static final int WAIT_SECOND = 1000;

    public static final OsInfo OS = new OsInfo();

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
        GlobalMemory memory = LAYER.getMemory();
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
        FileSystem fileSystem = SI.getOperatingSystem().getFileSystem();
        List<OSFileStore> fsArray = fileSystem.getFileStores();
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
        CentralProcessor processor = LAYER.getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(WAIT_SECOND);
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
        computer.setComputerIp(getHostIp());
        computer.setComputerMac(getHostMac());
        computer.setOsName(props.getProperty("os.name"));
        computer.setOsArch(props.getProperty("os.arch"));
        computer.setUserDir(props.getProperty("user.dir"));
        computer.setLastStartTime(getLastStartTime());
        computer.setRunTime(getRunTime());
        return computer;
    }

    /**
     * 最近启动时间
     */
    public static String getLastStartTime() {
        return DateUtils.format(new Date(OPERATING_SYSTEM.getSystemBootTime() * 1000L), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 运行时长
     *
     * @param dayUnit  单位：天
     * @param hourUnit 单位：小时
     * @param minUnit  单位：分钟
     */
    public static String getRunTime(String dayUnit, String hourUnit, String minUnit) {
        long systemUptime = OPERATING_SYSTEM.getSystemUptime();// 秒
        if (systemUptime < 60) {
            return systemUptime + "秒";
        } else if (systemUptime < 3600) {
            return systemUptime / 60 + minUnit;
        } else if (systemUptime < 86400) {// 3600 * 60
            return systemUptime / 3600 + hourUnit + systemUptime % 3600 / 60 + minUnit;
        } else {
            long hourOrMin = systemUptime % 86400;
            String hourOrMinWithUnit;
            if (hourOrMin > 3600) {
                hourOrMinWithUnit = hourOrMin / 3600 + hourUnit;
            } else {
                hourOrMinWithUnit = hourOrMin / 60 + minUnit;
            }
            return systemUptime / 86400 + dayUnit + hourOrMinWithUnit;
        }
    }

    /**
     * 运行时长（单位默认 天、小时分钟）
     */
    public static String getRunTime() {
        return getRunTime("天", "小时", "分钟");
    }

    /**
     * 主机ip
     */
    public static String getHostIp() {
        return Objects.requireNonNull(getLocalIp4Address()).getHostAddress();
    }

    /**
     * 主机mac
     */
    public static String getHostMac() {
        for (NetworkIF networkIF : HARDWARE.getNetworkIFs()) {
            if (!networkIF.isKnownVmMacAddr()) {
                return networkIF.getMacaddr();
            }
        }
        return null;
    }

    /**
     * 主机mac
     */
    public static List<String> getHostMacs() {
        List<String> macs = new ArrayList<>();
        for (NetworkIF networkIF : HARDWARE.getNetworkIFs()) {
            macs.add(networkIF.getMacaddr());
        }
        return macs;
    }

    /**
     * 主机名
     */
    public static String getHostName() {
        return OPERATING_SYSTEM.getNetworkParams().getHostName();
    }

    public static Inet4Address getLocalIp4Address() {
        List<Inet4Address> ipByNi = null;
        try {
            ipByNi = getLocalIp4AddressFromNetworkInterface();
            if (ipByNi.size() != 1) {
                final Inet4Address ipBySocketOpt = getIpBySocket();
                if (ipBySocketOpt != null) {
                    return ipBySocketOpt;
                } else {
                    return ipByNi.isEmpty() ? null : ipByNi.get(0);
                }
            }
        } catch (SocketException e) {
            LOGGER.error("getLocalIp4Address failed.", e);
        }
        return Objects.requireNonNull(ipByNi).get(0);
    }

    private static Inet4Address getIpBySocket() throws SocketException {
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            if (socket.getLocalAddress() instanceof Inet4Address) {
                return (Inet4Address) socket.getLocalAddress();
            }
        } catch (UnknownHostException e) {
            LOGGER.error("getIpBySocket failed.", e);
        }
        return null;
    }

    public static List<Inet4Address> getLocalIp4AddressFromNetworkInterface() throws SocketException {
        List<Inet4Address> addresses = new ArrayList<>(1);
        Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
        if (e == null) {
            return addresses;
        }
        while (e.hasMoreElements()) {
            NetworkInterface n = e.nextElement();
            if (!isValidInterface(n)) {
                continue;
            }
            Enumeration<InetAddress> ee = n.getInetAddresses();
            while (ee.hasMoreElements()) {
                InetAddress i = ee.nextElement();
                if (isValidAddress(i)) {
                    addresses.add((Inet4Address) i);
                }
            }
        }
        return addresses;
    }

    /**
     * 过滤回环网卡、点对点网卡、非活动网卡、虚拟网卡并要求网卡名字是eth或ens开头
     *
     * @param ni 网卡
     * @return 如果满足要求则true，否则false
     */
    private static boolean isValidInterface(NetworkInterface ni) throws SocketException {
        return !ni.isLoopback() && !ni.isPointToPoint() && ni.isUp() && !ni.isVirtual()
                && (ni.getName().startsWith("eth") || ni.getName().startsWith("ens"));
    }

    /**
     * 判断是否是IPv4，并且内网地址并过滤回环地址.
     */
    private static boolean isValidAddress(InetAddress address) {
        return address instanceof Inet4Address && address.isSiteLocalAddress() && !address.isLoopbackAddress();
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

    public static void main(String[] args) {
        System.out.println(getComputer());
    }
}
