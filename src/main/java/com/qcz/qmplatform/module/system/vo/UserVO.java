package com.qcz.qmplatform.module.system.vo;

import com.qcz.qmplatform.module.system.domain.User;

import java.util.List;

public class UserVO extends User {
    private List<String> organizationIds;

    private List<String> roleIds;

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public List<String> getOrganizationIds() {
        return organizationIds;
    }

    public void setOrganizationIds(List<String> organizationIds) {
        this.organizationIds = organizationIds;
    }
}
