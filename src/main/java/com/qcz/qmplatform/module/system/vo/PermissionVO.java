package com.qcz.qmplatform.module.system.vo;

import com.qcz.qmplatform.module.system.pojo.Permission;

public class PermissionVO extends Permission {

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
