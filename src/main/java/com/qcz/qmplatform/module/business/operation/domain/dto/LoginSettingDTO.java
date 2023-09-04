package com.qcz.qmplatform.module.business.operation.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class LoginSettingDTO implements Serializable {

    /**
     * 登录页标签
     */
    @NotBlank
    private String loginPageTitle;

    /**
     * 系统中文标题
     */
    @NotBlank
    private String systemChineseTitle;

    /**
     * 系统英文标题
     */
    private String systemEnglishTitle;

    /**
     * 账号登录标题
     */
    @NotBlank
    private String accountLoginTitle;

    /**
     * 忘记密码
     */
    private int enableForgetPassword;

    /**
     * 其它方式登录（多个逗号分割）
     */
    private String otherLoginWay;

    /**
     * 页尾信息
     */
    private String bottomInfo;
}
