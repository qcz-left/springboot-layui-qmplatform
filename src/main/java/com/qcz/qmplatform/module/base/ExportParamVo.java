package com.qcz.qmplatform.module.base;

import java.io.Serializable;

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
     * 生成的文件名称
     */
    private String generateName;

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
