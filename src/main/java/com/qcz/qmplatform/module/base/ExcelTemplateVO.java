package com.qcz.qmplatform.module.base;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

@Data
@Accessors(chain = true)
public class ExcelTemplateVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 生成的文件名称
     */
    private String generateName;

    /**
     * 列头
     */
    private Map<String, ExportColumn> colNames;

}
