package com.qcz.qmplatform.common.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Excel行记录封装信息
 */
@Data
@Accessors(chain = true)
public class ExcelRow<T> implements Serializable {

    /**
     * 行号
     */
    private long rowIndex;

    /**
     * 行数据
     */
    private T row;

}
