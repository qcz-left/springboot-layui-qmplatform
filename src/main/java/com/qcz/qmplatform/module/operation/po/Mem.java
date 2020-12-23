package com.qcz.qmplatform.module.operation.po;

import com.qcz.qmplatform.common.utils.Arith;

import java.io.Serializable;

/**
 * 内存
 */
public class Mem implements Serializable {
    /**
     * 内存总量
     */
    private String total;

    /**
     * 已用内存
     */
    private String used;

    /**
     * 剩余内存
     */
    private String free;

    /**
     * 使用率
     */
    private double usage;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public double getUsage() {
        return usage;
    }

    public void setUsage(double usage) {
        this.usage = usage;
    }

    @Override
    public String toString() {
        return "Mem{" +
                "total='" + total + '\'' +
                ", used='" + used + '\'' +
                ", free='" + free + '\'' +
                ", usage=" + usage +
                '}';
    }
}

