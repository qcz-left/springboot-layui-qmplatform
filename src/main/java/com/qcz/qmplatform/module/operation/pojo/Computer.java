package com.qcz.qmplatform.module.operation.pojo;

import java.io.Serializable;

public class Computer implements Serializable {

    /**
     * 计算机名称
     */
    private String computerName;
    /**
     * ip地址
     */
    private String computerIp;
    /**
     * mac地址
     */
    private String computerMac;
    /**
     * 操作系统名称
     */
    private String osName;
    /**
     * 系统类型
     */
    private String osArch;
    /**
     * 所在目录
     */
    private String userDir;
    /**
     * 上次启动时间
     */
    private String lastStartTime;
    /**
     * 运行时长
     */
    private String runTime;

    public String getComputerMac() {
        return computerMac;
    }

    public void setComputerMac(String computerMac) {
        this.computerMac = computerMac;
    }

    public String getLastStartTime() {
        return lastStartTime;
    }

    public void setLastStartTime(String lastStartTime) {
        this.lastStartTime = lastStartTime;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getComputerIp() {
        return computerIp;
    }

    public void setComputerIp(String computerIp) {
        this.computerIp = computerIp;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsArch() {
        return osArch;
    }

    public void setOsArch(String osArch) {
        this.osArch = osArch;
    }

    public String getUserDir() {
        return userDir;
    }

    public void setUserDir(String userDir) {
        this.userDir = userDir;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "computerName='" + computerName + '\'' +
                ", computerIp='" + computerIp + '\'' +
                ", computerMac='" + computerMac + '\'' +
                ", osName='" + osName + '\'' +
                ", osArch='" + osArch + '\'' +
                ", userDir='" + userDir + '\'' +
                ", lastStartTime='" + lastStartTime + '\'' +
                ", runTime='" + runTime + '\'' +
                '}';
    }
}
