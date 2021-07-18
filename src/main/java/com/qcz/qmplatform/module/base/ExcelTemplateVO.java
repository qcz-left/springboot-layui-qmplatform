package com.qcz.qmplatform.module.base;

import java.io.Serializable;
import java.util.Map;

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

    public String getGenerateName() {
        return generateName;
    }

    public void setGenerateName(String generateName) {
        this.generateName = generateName;
    }

    public Map<String, ExportColumn> getColNames() {
        return colNames;
    }

    public void setColNames(Map<String, ExportColumn> colNames) {
        this.colNames = colNames;
    }
}
