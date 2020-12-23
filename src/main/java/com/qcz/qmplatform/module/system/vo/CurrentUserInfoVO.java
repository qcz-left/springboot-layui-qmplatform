package com.qcz.qmplatform.module.system.vo;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class CurrentUserInfoVO implements Serializable {

    /**
     * 我的角色（多个,号隔开）
     */
    private String roleName;

    /**
     * 登录名
     */
    private String loginname;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 性别
     */
    private String userSex;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String emailAddr;

    /**
     * 备注
     */
    private String remark;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "CurrentUserInfoVO{" +
                "roleName='" + roleName + '\'' +
                ", loginname='" + loginname + '\'' +
                ", username='" + username + '\'' +
                ", userSex=" + userSex +
                ", phone='" + phone + '\'' +
                ", emailAddr='" + emailAddr + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
