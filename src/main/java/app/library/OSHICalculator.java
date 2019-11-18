package app.library;


import oshi.hardware.*;
import oshi.hardware.CentralProcessor.TickType;
import oshi.software.os.*;
import oshi.software.os.OperatingSystem.ProcessSort;
import oshi.util.FormatUtil;
import oshi.util.Util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Class OSHICalculator.
 *
 * @author Sadeem-pc
 */
public class OSHICalculator {

    /**ANSI Code color characters*/
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    private static final String REPLACE_REGEX = "[^0-9.]";


    public static long previousTime;
    public String CPU(){
        SystemInfo systemInfo = new SystemInfo();
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        int cpuNumber = processor.getLogicalProcessorCount();

        int processId = systemInfo.getOperatingSystem().getProcessId();
        OSProcess process = systemInfo.getOperatingSystem().getProcess(processId);
        long currentTime = process.getKernelTime() + process.getUserTime();
        long timeDifference = currentTime - previousTime;

        double processCpu = (100 * (timeDifference / 5000d)) / cpuNumber;
        previousTime = currentTime;

        String r = String.format("K: %d, U: %d, diff: %d, CPU: %.1f%n", process.getKernelTime(), process.getUserTime(),
                timeDifference, processCpu);

        return r;
    }

    public void printProcessesx( ) {
        SystemInfo systemInfo = new SystemInfo();
        OperatingSystem os = systemInfo.getOperatingSystem();
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        List<String> oshi = new ArrayList<>();
        oshi.add("Processes: " + os.getProcessCount() + ", Threads: " + os.getThreadCount());
        // Sort by highest CPU
        List<OSProcess> procs = Arrays.asList(os.getProcesses(5, ProcessSort.CPU));

        oshi.add("   PID  %CPU %MEM       VSZ       RSS Name");
        for (int i = 0; i < procs.size() ; i++) {
            OSProcess p = procs.get(i);
            oshi.add(String.format(" %5d %5.1f %4.1f %9s %9s %s", p.getProcessID(),
                    100d * (p.getKernelTime() + p.getUserTime()) / p.getUpTime(),
                    100d * p.getResidentSetSize() / memory.getTotal(), FormatUtil.formatBytes(p.getVirtualSize()),
                    FormatUtil.formatBytes(p.getResidentSetSize()), p.getName()));

            System.out.format(" %5d %5.1f %4.1f %9s %9s %s", p.getProcessID(),
                    100d * (p.getKernelTime() + p.getUserTime()) / p.getUpTime(),
                    100d * p.getResidentSetSize() / memory.getTotal(), FormatUtil.formatBytes(p.getVirtualSize()),
                    FormatUtil.formatBytes(p.getResidentSetSize()), p.getName());
            System.out.println("x");
        }


    }
    private static void printComputerSystem() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        ComputerSystem computerSystem = hal.getComputerSystem();
        System.out.println("manufacturer: " + computerSystem.getManufacturer());
        System.out.println("pojo: " + computerSystem.getModel());
        System.out.println("serialnumber: " + computerSystem.getSerialNumber());
        final Firmware firmware = computerSystem.getFirmware();
        System.out.println("firmware:");
        System.out.println("  manufacturer: " + firmware.getManufacturer());
        System.out.println("  name: " + firmware.getName());
        System.out.println("  description: " + firmware.getDescription());
        System.out.println("  version: " + firmware.getVersion());
        //System.out.println("  release date: " + (firmware.getReleaseDate() == null ? "unknown"
        //        : firmware.getReleaseDate() == null ? "unknown" : FormatUtil.formatDate(firmware.getReleaseDate())));
        final Baseboard baseboard = computerSystem.getBaseboard();
        System.out.println("baseboard:");
        System.out.println("  manufacturer: " + baseboard.getManufacturer());
        System.out.println("  pojo: " + baseboard.getModel());
        System.out.println("  version: " + baseboard.getVersion());
        System.out.println("  serialnumber: " + baseboard.getSerialNumber());
    }

    private static void printProcessor() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        CentralProcessor processor = hal.getProcessor();
        System.out.println(processor);
        System.out.println(" " + processor.getPhysicalPackageCount() + " physical CPU package(s)");
        System.out.println(" " + processor.getPhysicalProcessorCount() + " physical CPU core(s)");
        System.out.println(" " + processor.getLogicalProcessorCount() + " logical CPU(s)");

        System.out.println("Identifier: " + processor.getIdentifier());
        System.out.println("ProcessorID: " + processor.getProcessorID());
    }

    private static void printMemory() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        GlobalMemory memory = hal.getMemory();
        System.out.println("Memory: " + FormatUtil.formatBytes(memory.getAvailable()) + "/"
                + FormatUtil.formatBytes(memory.getTotal()));
        System.out.println("Swap used: " + FormatUtil.formatBytes(memory.getVirtualMemory().getSwapUsed()) + "/"
                + FormatUtil.formatBytes(memory.getTotal()));
    }

//-------------------------------------------------------

    public String getTotalMemory(){
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        GlobalMemory memory = hal.getMemory();
        long t = memory.getTotal();
        //        str1 = str1.replaceAll(REPLACE_REGEX, "");
//        double d = Double.parseDouble(str1);
        return FormatUtil.formatBytes(t);
    }

    public String getConsumedMemory(){
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        GlobalMemory memory = hal.getMemory();
        long longConsumed = (memory.getTotal()-memory.getAvailable());
        return FormatUtil.formatBytes(longConsumed);
//        strConsumed = strConsumed.replaceAll(REPLACE_REGEX, "");
//        double consumed = Double.parseDouble(strConsumed);
//        return round(consumed,1);
    }

    public double getMemoryPercentage(){
        double consumed = round(Double.parseDouble(getConsumedMemory().replaceAll(REPLACE_REGEX,"")),1);
        if (getConsumedMemory().contains("KiB")){
            consumed = (consumed / (1024*1024));
        }else if (getConsumedMemory().contains("MiB")){
            consumed = (consumed / 1024);
        }else if (getConsumedMemory().contains("GiB")){
            //SKIP
        }else if (getConsumedMemory().contains("TiB")){
            consumed = (consumed * 1024);
        }else if (getConsumedMemory().contains("PiB")){
            consumed = (consumed * (1024*1024));
        }else if (getConsumedMemory().contains("EiB")){
            consumed = (consumed * (1024*1024*1024));
        }
        double total = round(Double.parseDouble(getTotalMemory().replaceAll(REPLACE_REGEX,"")),1);

        return round(((consumed/total)*100),1);
    }

    public String getTotalSwapMemory(){
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        GlobalMemory memory = hal.getMemory();
        long t = memory.getVirtualMemory().getSwapTotal();
        return FormatUtil.formatBytes(t);
//        str1 = str1.replaceAll(REPLACE_REGEX, "");
//        double d = Double.parseDouble(str1);
//        return round(d,1);
    }

    public String getConsumedSwapMemory(){
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        GlobalMemory memory = hal.getMemory();
        long longConsumed = memory.getVirtualMemory().getSwapUsed();
        return FormatUtil.formatBytes(longConsumed);
//        strConsumed = strConsumed.replaceAll(REPLACE_REGEX, "");
//        double consumed = Double.parseDouble(strConsumed);
//        return round(consumed,1);
    }

    public double getSwapMemoryPercentage(){
        double consumed = round(Double.parseDouble(getConsumedSwapMemory().replaceAll(REPLACE_REGEX,"")),1);
        if (getConsumedSwapMemory().contains("KiB")){
            consumed = (consumed / (1024*1024));
        }else if (getConsumedSwapMemory().contains("MiB")){
            consumed = (consumed / 1024);
        }else if (getConsumedSwapMemory().contains("GiB")){
            //SKIP
        }else if (getConsumedSwapMemory().contains("TiB")){
            consumed = (consumed * 1024);
        }else if (getConsumedSwapMemory().contains("PiB")){
            consumed = (consumed * (1024*1024));
        }else if (getConsumedSwapMemory().contains("EiB")){
            consumed = (consumed * (1024*1024*1024));
        }
        double total = round(Double.parseDouble(getTotalSwapMemory().replaceAll(REPLACE_REGEX,"")),1);

        return round(((consumed/total)*100),1);
    }

    public double getCPUPercentage(){

        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        CentralProcessor processor = hal.getProcessor();

        return round(processor.getSystemCpuLoadTicks().length * 100,1);
    }

    public int getPhysicalCPUCount(){
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        CentralProcessor processor = hal.getProcessor();

        return processor.getPhysicalProcessorCount();
    }

    public String getCPUSpeed(){
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        CentralProcessor processor = hal.getProcessor();
        String[] arr = processor.getName().split(" ");
        String speed = arr[arr.length-1];
        speed = speed.substring(0,speed.length()-3)+" "+speed.substring(speed.length()-3,speed.length());
        return speed;
    }

    public String getUsedCPUSpeed(double percentage){
        SystemInfo si = new SystemInfo();
        String r = null;
        HardwareAbstractionLayer hal = si.getHardware();

        CentralProcessor processor = hal.getProcessor();
        double FULLSPEED = Double.parseDouble(getCPUSpeed().replaceAll(REPLACE_REGEX,""));
//        double percetnage = (processor.getSystemCpuLoad());
        double val = (FULLSPEED * percentage)/100;
        if (percentage == 0){val = 0;}




        long[] prevTicks = processor.getSystemCpuLoadTicks();

        long[][] prevProcTicks = processor.getProcessorCpuLoadTicks();
        double[] freqs = processor.getSystemLoadAverage(3);
        if (freqs[0] > 0) {
            StringBuilder sb = new StringBuilder("");
            for (int i = 0; i < freqs.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(freqs[i]);
            }
            r = sb.toString();
        }

        return r ;
    }

    //---------------------------------------------------------
    private static void printCpu() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        CentralProcessor processor = hal.getProcessor();
        System.out.println("Uptime: " + FormatUtil.formatElapsedSecs(si.getOperatingSystem().getSystemUptime()));
        System.out.println(
                "Context Switches/Interrupts: " + processor.getContextSwitches() + " / " + processor.getInterrupts());

        long[] prevTicks = processor.getSystemCpuLoadTicks();
        System.out.println("CPU, IOWait, and IRQ ticks @ 0 sec:" + Arrays.toString(prevTicks));
        // Wait a second...
        Util.sleep(1000);
        long[] ticks = processor.getSystemCpuLoadTicks();
        System.out.println("CPU, IOWait, and IRQ ticks @ 1 sec:" + Arrays.toString(ticks));
        long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
        long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
        long sys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
        long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
        long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
        long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
        long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
        long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
        long totalCpu = user + nice + sys + idle + iowait + irq + softirq + steal;

        System.out.format(
                "Admin: %.1f%% Nice: %.1f%% System: %.1f%% Idle: %.1f%% IOwait: %.1f%% IRQ: %.1f%% SoftIRQ: %.1f%% Steal: %.1f%%%n",
                100d * user / totalCpu, 100d * nice / totalCpu, 100d * sys / totalCpu, 100d * idle / totalCpu,
                100d * iowait / totalCpu, 100d * irq / totalCpu, 100d * softirq / totalCpu, 100d * steal / totalCpu);
        System.out.format("CPU load: %.1f%% (counting ticks)%n", processor.getSystemCpuLoadTicks().length * 100);
        System.out.format("CPU load: %.1f%% (OS MXBean)%n", processor.getProcessorCpuLoadTicks().length * 100);
        double[] loadAverage = processor.getSystemLoadAverage(3);
        System.out.println("CPU load averages:" + (loadAverage[0] < 0 ? " N/A" : String.format(" %.2f", loadAverage[0]))
                + (loadAverage[1] < 0 ? " N/A" : String.format(" %.2f", loadAverage[1]))
                + (loadAverage[2] < 0 ? " N/A" : String.format(" %.2f", loadAverage[2])));
        // per core CPU
        StringBuilder procCpu = new StringBuilder("CPU load per processor:");
        long[] load = processor.getSystemCpuLoadTicks();
        for (double avg : load) {
            procCpu.append(String.format(" %.1f%%", avg * 100));
        }
        System.out.println(procCpu.toString());
    }




    private static void printProcesses() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        OperatingSystem os = si.getOperatingSystem();

        GlobalMemory memory = hal.getMemory();
        System.out.println("Processes: " + os.getProcessCount() + ", Threads: " + os.getThreadCount());
        // Sort by highest CPU
        List<OSProcess> procs = Arrays.asList(os.getProcesses(5, ProcessSort.CPU));

        System.out.println("   PID  %CPU %MEM       VSZ       RSS Name");
        for (int i = 0; i < procs.size() && i < 5; i++) {
            OSProcess p = procs.get(i);
            System.out.format(" %5d %5.1f %4.1f %9s %9s %s%n", p.getProcessID(),
                    100d * (p.getKernelTime() + p.getUserTime()) / p.getUpTime(),
                    100d * p.getResidentSetSize() / memory.getTotal(), FormatUtil.formatBytes(p.getVirtualSize()),
                    FormatUtil.formatBytes(p.getResidentSetSize()), p.getName());
        }
    }

    private static void printSensors() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        Sensors sensors = hal.getSensors();
        System.out.println("Sensors:");
        System.out.format(" CPU Temperature: %.1f°C%n", sensors.getCpuTemperature());
        System.out.println(" Fan Speeds: " + Arrays.toString(sensors.getFanSpeeds()));
        System.out.format(" CPU Voltage: %.1fV%n", sensors.getCpuVoltage());
    }

    private static void printPowerSources() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        PowerSource[] powerSources = hal.getPowerSources();
        StringBuilder sb = new StringBuilder("Power: ");
        if (powerSources.length == 0) {
            sb.append("Unknown");
        } else {
            double timeRemaining = powerSources[0].getTimeRemaining();
            if (timeRemaining < -1d) {
                sb.append("Charging");
            } else if (timeRemaining < 0d) {
                sb.append("Calculating time remaining");
            } else {
                sb.append(String.format("%d:%02d remaining", (int) (timeRemaining / 3600),
                        (int) (timeRemaining / 60) % 60));
            }
        }
        for (PowerSource pSource : powerSources) {
            sb.append(String.format("%n %s @ %.1f%%", pSource.getName(), pSource.getRemainingCapacity() * 100d));
        }
        System.out.println(sb.toString());
    }

    public String getDiskSize(){
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        HWDiskStore[] diskStores = hal.getDiskStores();
        long l = 0;
        for (HWDiskStore disk : diskStores) {
            HWPartition[] partitions = disk.getPartitions();
            if (partitions == null) {
                // TODO Remove when all OS's implemented
                continue;
            }
            for (HWPartition part : partitions) {
                l += part.getSize();
            }
        }
        return FormatUtil.formatBytesDecimal(l);
//        String con= str.replaceAll(REPLACE_REGEX, "");
//        return round(Double.parseDouble(con),1);
    }

    public String getConsumedSpace(){
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();

        long free = 0, full = 0;
        FileSystem fileSystem = os.getFileSystem();

        OSFileStore[] fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            free += fs.getUsableSpace();
            full += fs.getTotalSpace();
        }
        long consumed = full - free;
        return FormatUtil.formatBytesDecimal(consumed);
//        String con= str.replaceAll(REPLACE_REGEX, "");
//        return round(Double.parseDouble(con),1);
    }

    public double getDiskPercentage(){
        double consumed = round(Double.parseDouble(getConsumedSpace().replaceAll(REPLACE_REGEX,"")),1);
        if (getConsumedSpace().contains("KiB")){
            consumed = (consumed / (1024*1024));
        }else if (getConsumedSpace().contains("MiB")){
            consumed = (consumed / 1024);
        }else if (getConsumedSpace().contains("GiB")){
            //SKIP
        }else if (getConsumedSpace().contains("TiB")){
            consumed = (consumed * 1024);
        }else if (getConsumedSpace().contains("PiB")){
            consumed = (consumed * (1024*1024));
        }else if (getConsumedSpace().contains("EiB")){
            consumed = (consumed * (1024*1024*1024));
        }
        double total = round(Double.parseDouble(getDiskSize().replaceAll(REPLACE_REGEX,"")),1);

        return round(((consumed/total)*100),1);
    }

    private static void printDisks() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        HWDiskStore[] diskStores = hal.getDiskStores();
        System.out.println("Disks:");
        for (HWDiskStore disk : diskStores) {
            boolean readwrite = disk.getReads() > 0 || disk.getWrites() > 0;
            System.out.format(" %s: (pojo: %s - S/N: %s) size: %s, reads: %s (%s), writes: %s (%s), xfer: %s ms%n",
                    disk.getName(), disk.getModel(), disk.getSerial(),
                    disk.getSize() > 0 ? FormatUtil.formatBytesDecimal(disk.getSize()) : "?",
                    readwrite ? disk.getReads() : "?", readwrite ? FormatUtil.formatBytes(disk.getReadBytes()) : "?",
                    readwrite ? disk.getWrites() : "?", readwrite ? FormatUtil.formatBytes(disk.getWriteBytes()) : "?",
                    readwrite ? disk.getTransferTime() : "?");
            HWPartition[] partitions = disk.getPartitions();
            if (partitions == null) {
                // TODO Remove when all OS's implemented
                continue;
            }
            for (HWPartition part : partitions) {
                System.out.format(" |-- %s: %s (%s) Maj:Min=%d:%d, size: %s%s%n", part.getIdentification(),
                        part.getName(), part.getType(), part.getMajor(), part.getMinor(),
                        FormatUtil.formatBytesDecimal(part.getSize()),
                        part.getMountPoint().isEmpty() ? "" : " @ " + part.getMountPoint());
            }
        }
    }

    private static void printFileSystem() {
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();

        FileSystem fileSystem = os.getFileSystem();
        System.out.println("File System:");

        System.out.format(" File Descriptors: %d/%d%n", fileSystem.getOpenFileDescriptors(),
                fileSystem.getMaxFileDescriptors());

        OSFileStore[] fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            long usable = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            System.out.format(
                    " %s (%s) [%s] %s of %s free (%.1f%%) is %s "
                            + (fs.getLogicalVolume() != null && fs.getLogicalVolume().length() > 0 ? "[%s]" : "%s")
                            + " and is mounted at %s%n",
                    fs.getName(), fs.getDescription().isEmpty() ? "file system" : fs.getDescription(), fs.getType(),
                    FormatUtil.formatBytes(usable), FormatUtil.formatBytes(fs.getTotalSpace()), 100d * usable / total,
                    fs.getVolume(), fs.getLogicalVolume(), fs.getMount());
        }
    }

    private static void printNetworkInterfaces() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        NetworkIF[] networkIFs = hal.getNetworkIFs();
        System.out.println("Network interfaces:");
        for (NetworkIF net : networkIFs) {
            System.out.format(" Name: %s (%s)%n", net.getName(), net.getDisplayName());
            System.out.format("   MAC Address: %s %n", net.getMacaddr());
            System.out.format("   MTU: %s, Speed: %s %n", net.getMTU(), FormatUtil.formatValue(net.getSpeed(), "bps"));
            System.out.format("   IPv4: %s %n", Arrays.toString(net.getIPv4addr()));
            System.out.format("   IPv6: %s %n", Arrays.toString(net.getIPv6addr()));
            boolean hasData = net.getBytesRecv() > 0 || net.getBytesSent() > 0 || net.getPacketsRecv() > 0
                    || net.getPacketsSent() > 0;
            System.out.format("   Traffic: received %s/%s%s; transmitted %s/%s%s %n",
                    hasData ? net.getPacketsRecv() + " packets" : "?",
                    hasData ? FormatUtil.formatBytes(net.getBytesRecv()) : "?",
                    hasData ? " (" + net.getInErrors() + " err)" : "",
                    hasData ? net.getPacketsSent() + " packets" : "?",
                    hasData ? FormatUtil.formatBytes(net.getBytesSent()) : "?",
                    hasData ? " (" + net.getOutErrors() + " err)" : "");
        }
    }

    private static void printNetworkParameters() {
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();

        NetworkParams networkParams = os.getNetworkParams();
        System.out.println("Network parameters:");
        System.out.format(" Host name: %s%n", networkParams.getHostName());
        System.out.format(" Domain name: %s%n", networkParams.getDomainName());
        System.out.format(" DNS servers: %s%n", Arrays.toString(networkParams.getDnsServers()));
        System.out.format(" IPv4 Gateway: %s%n", networkParams.getIpv4DefaultGateway());
        System.out.format(" IPv6 Gateway: %s%n", networkParams.getIpv6DefaultGateway());
    }

    private static void printDisplays() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        Display[] displays = hal.getDisplays();
        System.out.println("Displays:");
        int i = 0;
        for (Display display : displays) {
            System.out.println(" Display " + i + ":");
            System.out.println(display.toString());
            i++;
        }
    }

    private static void printUsbDevices() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        UsbDevice[] usbDevices = hal.getUsbDevices(true);
        System.out.println("USB Devices:");
        for (UsbDevice usbDevice : usbDevices) {
            System.out.println(usbDevice.toString());
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        if (Double.isInfinite(value) || Double.isNaN(value)){
            return 0.0;
        }else
        {
            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }


    }
}
