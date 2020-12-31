package com.qcz.qmplatform.module.operation.pojo;

import java.io.Serializable;
import java.util.List;

public class Disk implements Serializable {

    private List<SysFile> sysFiles;

    /**
     * 总大小
     */
    private String total;

    /**
     * 总剩余大小
     */
    private String free;

    /**
     * 总已经使用量
     */
    private String used;

    /**
     * 使用率
     */
    private double usage;

    public List<SysFile> getSysFiles() {
        return sysFiles;
    }

    public void setSysFiles(List<SysFile> sysFiles) {
        this.sysFiles = sysFiles;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public double getUsage() {
        return usage;
    }

    public void setUsage(double usage) {
        this.usage = usage;
    }

    @Override
    public String toString() {
        return "Disk{" +
                "sysFiles=" + sysFiles +
                ", total='" + total + '\'' +
                ", free='" + free + '\'' +
                ", used='" + used + '\'' +
                ", usage=" + usage +
                '}';
    }
}
