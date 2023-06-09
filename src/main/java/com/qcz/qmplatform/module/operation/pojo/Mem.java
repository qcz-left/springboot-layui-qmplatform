package com.qcz.qmplatform.module.operation.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 内存
 */
@Data
@Accessors(chain = true)
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

}

