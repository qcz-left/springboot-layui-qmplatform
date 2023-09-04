package com.qcz.qmplatform.module.business.notify.domain.pojo;

/**
 * 短信模板类型
 */
public enum TemplateType {

    /**
     * 验证码
     */
    VALIDATE_CODE(1),

    /**
     * 告警
     */
    ALARM(2),

    /**
     * 广播通知
     */
    NOTIFY(3);

    private final int type;

    TemplateType(int type) {
        this.type = type;
    }

    public int type() {
        return type;
    }
}
