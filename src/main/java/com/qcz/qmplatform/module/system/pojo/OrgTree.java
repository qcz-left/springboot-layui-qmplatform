package com.qcz.qmplatform.module.system.pojo;

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

    /**
     * 类型（1：部门；2：用户）
     */
    private Integer itype;

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

    public Integer getItype() {
        return itype;
    }

    public void setItype(Integer itype) {
        this.itype = itype;
    }
}
