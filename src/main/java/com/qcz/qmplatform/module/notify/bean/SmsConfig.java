package com.qcz.qmplatform.module.notify.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SmsConfig implements Serializable {

    /**
     * 秘钥id
     */
    private String secretId;

    /**
     * 秘钥
     */
    private String secretKey;

    /**
     * 调用api域名
     */
    private String endpoint;

    /**
     * 短信应用id
     */
    private String appId;

    /**
     * 短信签名
     */
    private String sign;

    /**
     * 模板id
     */
    private String templateID;

    /**
     * 模板参数
     */
    private Map<String, String> templateParams;

    /**
     * 手机号码
     */
    private List<String> phones;

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

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
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

    public String getTemplateID() {
        return templateID;
    }

    public void setTemplateID(String templateID) {
        this.templateID = templateID;
    }

    public Map<String, String> getTemplateParams() {
        return templateParams;
    }

    public void setTemplateParams(Map<String, String> templateParams) {
        this.templateParams = templateParams;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }
}
