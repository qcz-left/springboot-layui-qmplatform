package com.qcz.qmplatform.module.system.vo;

import com.qcz.qmplatform.common.anno.ExcelField;
import com.qcz.qmplatform.module.system.domain.User;

import java.util.List;

public class UserVO extends User {

    private List<String> organizationIds;

    private List<String> roleIds;

    @ExcelField("性别")
    private String userSexName;

    @ExcelField("状态")
    private String lockedName;

    @ExcelField("所属部门")
    private String organizationName;

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getUserSexName() {
        return userSexName;
    }

    public void setUserSexName(String userSexName) {
        this.userSexName = userSexName;
    }

    public String getLockedName() {
        return lockedName;
    }

    public void setLockedName(String lockedName) {
        this.lockedName = lockedName;
    }

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
