package com.qcz.qmplatform.module.system.domain.assist;

/**
 * 系统信息发送对象
 */
public enum MessageReceiver {

    /**
     * 所有人
     */
    ALL("all"),

    /**
     * 管理者
     */
    ADMIN("admin");

    private final String receiver;

    MessageReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiver() {
        return receiver;
    }
}
