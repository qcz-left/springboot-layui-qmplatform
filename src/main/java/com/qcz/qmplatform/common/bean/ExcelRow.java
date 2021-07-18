package com.qcz.qmplatform.common.bean;

import java.io.Serializable;

public class ExcelRow<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int rowIndex;

    private T row;

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public T getRow() {
        return row;
    }

    public void setRow(T row) {
        this.row = row;
    }
}
