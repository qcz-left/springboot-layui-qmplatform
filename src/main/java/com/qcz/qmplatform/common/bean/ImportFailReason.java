package com.qcz.qmplatform.common.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 导入失败原因
 */
@Data
@Accessors(chain = true)
public class ImportFailReason implements Serializable {

    /**
     * 行号
     */
    private long rowIndex;

    /**
     * 名称
     */
    private String name;

    /**
     * 原因
     */
    private String reason;

}
