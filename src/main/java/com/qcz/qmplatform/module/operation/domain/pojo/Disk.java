package com.qcz.qmplatform.module.operation.domain.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
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

}
