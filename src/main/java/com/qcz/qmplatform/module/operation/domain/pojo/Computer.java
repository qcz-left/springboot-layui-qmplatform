package com.qcz.qmplatform.module.operation.domain.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
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

}
