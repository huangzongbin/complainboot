package com.krt.sys.util;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OSFileStore;
import oshi.util.FormatUtil;
import oshi.util.Util;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * 系统监控信息
 *
 * @author 殷帅
 * @version 1.0
 * @date 2019年04月11日
 */
public class SysInfoUtils {

    /**
     * 获取cpu 和 内存使用信息
     *
     * @return {@link Map}
     */
    public static Map<String, Object> cpuMemory() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hardware = si.getHardware();
        CentralProcessor centralProcessor = hardware.getProcessor();
        long[] prevTicks = centralProcessor.getSystemCpuLoadTicks();
        Util.sleep(1000L);
        long[] ticks = centralProcessor.getSystemCpuLoadTicks();
        long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
        long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
        long system = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
        long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
        long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
        long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
        long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
        long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
        long totalCpu = user + nice + system + idle + iowait + irq + softirq + steal;
        HashMap<String, Object> map = new HashMap<>(3);
        HashMap<String, Object> cpuMap = new HashMap<>(8);
        cpuMap.put("name", centralProcessor.getName());
        String temp = "@";
        if (centralProcessor.getName().contains(temp)) {
            cpuMap.put("power", centralProcessor.getName().split(temp)[1]);
        }
        cpuMap.put("kernel", centralProcessor.getPhysicalProcessorCount());
        cpuMap.put("used", String.format("%.1f%%", 100.0D * (double) (user + system) / (double) totalCpu));
        cpuMap.put("user", String.format("%.1f%%", 100.0D * (double) user / (double) totalCpu));
        cpuMap.put("system", String.format("%.1f%%", 100.0D * (double) system / (double) totalCpu));
        cpuMap.put("nice", String.format("%.1f%%", 100.0D * (double) nice / (double) totalCpu));
        cpuMap.put("idle", String.format("%.1f%%", 100.0D * (double) idle / (double) totalCpu));
        map.put("cpu", cpuMap);
        GlobalMemory memory = hardware.getMemory();
        long available = memory.getAvailable();
        long total = memory.getTotal();
        long used = total - available;
        HashMap<String, Object> systemMap = new HashMap<>(5);
        systemMap.put("total", FormatUtil.formatBytesDecimal(total));
        systemMap.put("used", FormatUtil.formatBytesDecimal(used));
        systemMap.put("usedPercentage", String.format("%.1f%%", 100.0D * (double) used / (double) total));
        systemMap.put("available", FormatUtil.formatBytesDecimal(available));
        systemMap.put("availablePercentage", String.format("%.1f%%", 100.0D * (double) available / (double) total));
        map.put("system", systemMap);
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        Map<String, Object> jvmMap = new HashMap<>(5);
        MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage();
        long maxMemoryUsage = memoryUsage.getMax();
        jvmMap.put("total", FormatUtil.formatBytesDecimal(maxMemoryUsage));
        jvmMap.put("used", FormatUtil.formatBytesDecimal(memoryUsage.getUsed()));
        jvmMap.put("usedPercentage", String.format("%.1f%%", 100.0D * (double) memoryUsage.getUsed() / (double) maxMemoryUsage));
        long availableJvm = memoryUsage.getMax() - memoryUsage.getUsed();
        jvmMap.put("available", FormatUtil.formatBytesDecimal(availableJvm));
        jvmMap.put("availablePercentage", String.format("%.1f%%", 100.0D * (double) availableJvm / (double) maxMemoryUsage));
        map.put("jvm", jvmMap);
        return map;
    }

    /**
     * 获取jvm 堆栈信息
     *
     * @return {@link Map}
     */
    public static Map<String, Object> jvmHeapDisk() {
        HashMap<String, Object> map = new HashMap<>(3);
        MemoryMXBean centralProcessor = ManagementFactory.getMemoryMXBean();
        MemoryUsage prevTicks = centralProcessor.getHeapMemoryUsage();
        Map<String, Object> heapMap = new HashMap<>(4);
        heapMap.put("init", FormatUtil.formatBytesDecimal(prevTicks.getInit()));
        heapMap.put("committed", FormatUtil.formatBytesDecimal(prevTicks.getCommitted()));
        heapMap.put("max", FormatUtil.formatBytesDecimal(prevTicks.getMax()));
        heapMap.put("used", FormatUtil.formatBytesDecimal(prevTicks.getUsed()));
        map.put("heap", heapMap);
        MemoryUsage user = centralProcessor.getNonHeapMemoryUsage();
        Map<String, Object> nonHeap = new HashMap<>(4);
        nonHeap.put("init", FormatUtil.formatBytesDecimal(user.getInit()));
        nonHeap.put("committed", FormatUtil.formatBytesDecimal(user.getCommitted()));
        nonHeap.put("max", FormatUtil.formatBytesDecimal(user.getMax()));
        nonHeap.put("used", FormatUtil.formatBytesDecimal(user.getUsed()));
        map.put("nonHeap", nonHeap);
        SystemInfo nice = new SystemInfo();
        OSFileStore[] fsArray = nice.getOperatingSystem().getFileSystem().getFileStores();
        ArrayList<Object> system = new ArrayList<>();
        for (OSFileStore fs : fsArray) {
            Map<String, Object> fsInfo = new HashMap<>(6);
            fsInfo.put("name", fs.getName());
            fsInfo.put("mount", fs.getMount());
            fsInfo.put("type", fs.getType());
            long usableSpace = fs.getUsableSpace();
            long totalSpace = fs.getTotalSpace();
            fsInfo.put("total", FormatUtil.formatBytesDecimal(totalSpace));
            fsInfo.put("usable", FormatUtil.formatBytesDecimal(usableSpace));
            fsInfo.put("percentage", String.format("%.1f%%", 100.0D * (double) (totalSpace - usableSpace) / (double) totalSpace));
            system.add(fsInfo);
        }
        map.put("diskList", system);
        return map;
    }
}
