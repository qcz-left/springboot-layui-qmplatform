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
        return "Sys{" +
                "computerName='" + computerName + '\'' +
                ", computerIp='" + computerIp + '\'' +
                ", osName='" + osName + '\'' +
                ", osArch='" + osArch + '\'' +
                ", userDir='" + userDir + '\'' +
                '}';
    }
}
