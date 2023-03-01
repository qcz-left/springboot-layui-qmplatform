package com.qcz.qmplatform.common.bean;

import java.io.Serializable;

public class ImportFailReason implements Serializable {
    private static final long serialVersionUID = 1L;

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

    public long getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(long rowIndex) {
        this.rowIndex = rowIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
