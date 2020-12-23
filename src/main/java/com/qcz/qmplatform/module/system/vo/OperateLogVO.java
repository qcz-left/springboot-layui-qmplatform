package com.qcz.qmplatform.module.system.vo;

import com.qcz.qmplatform.module.system.domain.OperateLog;

public class OperateLogVO extends OperateLog {
    private String operateTypeName;

    private String operateStatusName;

    public String getOperateTypeName() {
        return operateTypeName;
    }

    public void setOperateTypeName(String operateTypeName) {
        this.operateTypeName = operateTypeName;
    }

    public String getOperateStatusName() {
        return operateStatusName;
    }

    public void setOperateStatusName(String operateStatusName) {
        this.operateStatusName = operateStatusName;
    }

}
