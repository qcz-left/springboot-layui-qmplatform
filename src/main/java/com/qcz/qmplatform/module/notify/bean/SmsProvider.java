package com.qcz.qmplatform.module.notify.bean;

/**
 * 短信提供商
 */
public enum SmsProvider {

    /**
     * 腾讯
     */
    TENCENT(1),

    /**
     * 阿里
     */
    ALI(2),

    /**
     * 华为
     */
    HUAWEI(3),

    /**
     * 百度
     */
    BAIDU(4);

    private final int code;

    SmsProvider(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }

    public static SmsProvider valueOf(int code) {
        SmsProvider[] smsProviders = values();
        for (SmsProvider smsProvider : smsProviders) {
            if (smsProvider.code == code) {
                return smsProvider;
            }
        }

        return null;
    }
}
