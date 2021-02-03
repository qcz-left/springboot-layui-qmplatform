package com.qcz.qmplatform.module.base;

import java.io.Serializable;

/**
 * 导出列
 */
public class ExportColumn implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 列头
     */
    private String title;

    /**
     * 列宽
     */
    private int width;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
