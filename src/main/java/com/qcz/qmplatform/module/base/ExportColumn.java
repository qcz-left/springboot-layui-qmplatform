package com.qcz.qmplatform.module.base;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 导出列
 */
@Data
@Accessors(chain = true)
public class ExportColumn implements Serializable {

    /**
     * 列头
     */
    private String title;

    /**
     * 列宽
     */
    private int width;

}
