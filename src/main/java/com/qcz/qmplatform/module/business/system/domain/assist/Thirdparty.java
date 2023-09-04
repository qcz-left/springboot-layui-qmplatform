package com.qcz.qmplatform.module.business.system.domain.assist;

/**
 * 第三方服务
 */
public enum Thirdparty {

    /**
     * 钉钉
     */
    DINGTALK("dingtalk");

    private final String name;

    Thirdparty(String name) {
        this.name = name;
    }

    public String getType() {
        return name;
    }
}
