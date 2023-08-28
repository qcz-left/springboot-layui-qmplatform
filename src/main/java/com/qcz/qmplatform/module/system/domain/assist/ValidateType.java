package com.qcz.qmplatform.module.system.domain.assist;

/**
 * 修改密码时所用的验证方式
 */
public enum ValidateType {

    /**
     * 密码
     */
    PASSWORD(1),
    /**
     * 手机短信
     */
    PHONE_SMS(2),
    /**
     * 邮件
     */
    EMAIL(3);

    private final int type;

    ValidateType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
