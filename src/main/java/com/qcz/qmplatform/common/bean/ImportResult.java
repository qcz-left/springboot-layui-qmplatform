package com.qcz.qmplatform.common.bean;

import java.io.Serializable;
import java.util.List;

public class ImportResult implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    private String title;

    /**
     * 导入总记录数
     */
    private int total;

    /**
     * 导入成功记录数
     */
    private int successCount;

    /**
     * 导入失败记录数
     */
    private int failCount;

    private List<ImportFailReason> importFailReasonList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    public List<ImportFailReason> getImportFailReasonList() {
        return importFailReasonList;
    }

    public void setImportFailReasonList(List<ImportFailReason> importFailReasonList) {
        this.importFailReasonList = importFailReasonList;
    }
}
