package com.qcz.qmplatform.module.base;

import java.util.Map;

/**
 * 导出参数
 */
public class ExportParamVO extends ExcelTemplateVO {

    private static final long serialVersionUID = 1L;

    /**
     * 查询数据的url
     */
    private String queryUrl;

    /**
     * 查询参数
     */
    private Map<String, Object> queryParam;

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

}
