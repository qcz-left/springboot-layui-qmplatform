package com.qcz.qmplatform.module.system.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class PasswordVO implements Serializable {

    /**
     * 登录名
     */
    private String loginname;

    /**
     * 原密码
     */
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

    /**
     * 验证码
     */
    private String validateCode;

    /**
     * 验证方式类型
     */
    private int validateType;

}
