package com.qcz.qmplatform.module.base;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 导出列
 */
@Data
@Accessors(chain = true)
public class ExportColumn implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 列头
     */
    private String title;

    /**
     * 列宽
     */
    private int width;

    /**
     * 是否下拉框
     */
    private boolean select;

    /**
     * 下拉框的值
     */
    private List<String> selectArray;

}
