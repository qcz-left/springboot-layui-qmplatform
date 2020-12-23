package com.qcz.qmplatform.module.system.po;

import com.qcz.qmplatform.common.bean.Tree;

public class OrgTree extends Tree {
    /**
     * 权限码（权限标识）
     */
    private String code;

    /**
     * 排序
     */
    private Integer iorder;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getIorder() {
        return iorder;
    }

    public void setIorder(Integer iorder) {
        this.iorder = iorder;
    }
}
