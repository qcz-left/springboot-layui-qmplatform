package com.qcz.qmplatform.module.other.vo;

import java.io.Serializable;

public class NotepadVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    private String title;

    /**
     * 只查询自己
     */
    private boolean onlySelf;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isOnlySelf() {
        return onlySelf;
    }

    public void setOnlySelf(boolean onlySelf) {
        this.onlySelf = onlySelf;
    }
}
