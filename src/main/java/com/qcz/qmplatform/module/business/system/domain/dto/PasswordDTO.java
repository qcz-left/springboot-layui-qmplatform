package com.qcz.qmplatform.module.business.system.domain.dto;

import com.qcz.qmplatform.common.validation.groups.Update;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class PasswordDTO implements Serializable {

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
    @NotBlank(message = "原密码不能为空", groups = {Update.class})
    private String newPassword;

    /**
     * 确认新密码
     */
    @NotBlank(message = "确认密码不能为空", groups = {Update.class})
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
