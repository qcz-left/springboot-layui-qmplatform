package com.qcz.qmplatform.module.notify.vo;

import com.qcz.qmplatform.module.notify.bean.SmsProvider;
import com.qcz.qmplatform.module.notify.bean.TemplateParam;

import java.io.Serializable;
import java.util.Map;

public class SmsConfigVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 秘钥id
     */
    private String secretId;

    /**
     * 秘钥
     */
    private String secretKey;

    /**
     * 短信应用id
     */
    private String appId;

    /**
     * 短信应用Key
     */
    private String appKey;

    /**
     * 短信签名
     */
    private String sign;

    /**
     * @see SmsProvider
     */
    private int smsProvider;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    /**
     * 模板参数列表
     */
    private Map<Integer, TemplateParam> templateParams;

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getSmsProvider() {
        return smsProvider;
    }

    public void setSmsProvider(int smsProvider) {
        this.smsProvider = smsProvider;
    }

    public Map<Integer, TemplateParam> getTemplateParams() {
        return templateParams;
    }

    public void setTemplateParams(Map<Integer, TemplateParam> templateParams) {
        this.templateParams = templateParams;
    }
}
