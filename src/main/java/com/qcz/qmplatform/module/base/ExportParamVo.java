package com.qcz.qmplatform.module.base;

import java.io.Serializable;
import java.util.Map;

/**
 * 导出参数
 */
public class ExportParamVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 查询数据的url
     */
    private String queryUrl;

    /**
     * 查询参数
     */
    private Map<String, Object> queryParam;

    /**
     * 生成的文件名称
     */
    private String generateName;

    /**
     * 列头
     */
    private Map<String, String> colNames;

    public Map<String, String> getColNames() {
        return colNames;
    }

    public void setColNames(Map<String, String> colNames) {
        this.colNames = colNames;
    }

    public Map<String, Object> getQueryParam() {
        return queryParam;
    }

    public void setQueryParam(Map<String, Object> queryParam) {
        this.queryParam = queryParam;
    }

    public String getQueryUrl() {
        return queryUrl;
    }

    public void setQueryUrl(String queryUrl) {
        this.queryUrl = queryUrl;
    }

    public String getGenerateName() {
        return generateName;
    }

    public void setGenerateName(String generateName) {
        this.generateName = generateName;
    }
}
