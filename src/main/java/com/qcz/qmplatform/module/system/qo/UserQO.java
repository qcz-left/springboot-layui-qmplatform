package com.qcz.qmplatform.module.system.qo;

import java.io.Serializable;
import java.util.List;

/**
 * 用户列表查询参数
 */
public class UserQO implements Serializable {
    private static final long serialVersionUID = 7627789571911402384L;

    private String username;
    private String userSex;
    private List<String> organizationIds;
    private String organizationIdsStr;

    /**
     * 所属部门是否精确查询
     */
    private int organizationExact;

    public int getOrganizationExact() {
        return organizationExact;
    }

    public void setOrganizationExact(int organizationExact) {
        this.organizationExact = organizationExact;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public List<String> getOrganizationIds() {
        return organizationIds;
    }

    public void setOrganizationIds(List<String> organizationIds) {
        this.organizationIds = organizationIds;
    }

    public String getOrganizationIdsStr() {
        return organizationIdsStr;
    }

    public void setOrganizationIdsStr(String organizationIdsStr) {
        this.organizationIdsStr = organizationIdsStr;
    }
}
