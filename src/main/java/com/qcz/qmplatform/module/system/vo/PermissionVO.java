package com.qcz.qmplatform.module.system.vo;

import com.qcz.qmplatform.module.system.pojo.Permission;

import java.util.List;

public class PermissionVO extends Permission {

    private String userId;

    public List<String> getNotExistsMenuIds() {
        return notExistsMenuIds;
    }

    public void setNotExistsMenuIds(List<String> notExistsMenuIds) {
        this.notExistsMenuIds = notExistsMenuIds;
    }

    /**
     * select中排除的菜单ID
     */
    private List<String> notExistsMenuIds;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
