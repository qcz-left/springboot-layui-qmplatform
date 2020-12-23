package com.qcz.qmplatform.module.system.vo;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class PasswordVO implements Serializable {

    /**
     * 原密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 新密码
     */
    @NotBlank(message = "原密码不能为空")
    private String newPassword;

    /**
     * 确认新密码
     */
    @NotBlank(message = "确认密码不能为空")
    private String confirmNewPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
