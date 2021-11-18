package com.qcz.qmplatform.module.system.assist;

/**
 * 登录类型
 */
public enum LoginType {
    PASSWORD("password"), // 密码登录
    NO_PASSWORD("no_password"); // 免密登录
    private String code;// 状态值

    LoginType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
