package com.qcz.qmplatform.module.system.pojo;

import java.io.Serializable;

/**
 *
 */
public class Permission implements Serializable {

    private String permissionId;

    private String permissionName;

    private String icon;

    private String code;

    private Integer iorder;

    private String parentId;

    private Integer permissionType;

    private String linkUrl;

    /**
     * 是否显示：0 不显示；1 显示
     */
    private Integer display;

    public int getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getIorder() {
        return iorder;
    }

    public void setIorder(Integer iorder) {
        this.iorder = iorder;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(Integer permissionType) {
        this.permissionType = permissionType;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "permissionId='" + permissionId + '\'' +
                ", permissionName='" + permissionName + '\'' +
                ", icon='" + icon + '\'' +
                ", code='" + code + '\'' +
                ", iorder=" + iorder +
                ", parentId='" + parentId + '\'' +
                ", permissionType=" + permissionType +
                ", linkUrl='" + linkUrl + '\'' +
                ", display=" + display +
                '}';
    }
}
