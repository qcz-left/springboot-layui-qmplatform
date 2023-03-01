package com.qcz.qmplatform.common.bean;

import java.io.Serializable;

public class ExcelRow<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private long rowIndex;

    private T row;

    public long getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(long rowIndex) {
        this.rowIndex = rowIndex;
    }

    public T getRow() {
        return row;
    }

    public void setRow(T row) {
        this.row = row;
    }
}
