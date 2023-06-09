package com.qcz.qmplatform.module.notify.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
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
     * 短信应用Key
     */
    private String appKey;

    /**
     * 短信签名
     */
    private String sign;

    /**
     * 通道号（华为云使用）
     */
    private String channelNumber;

    /**
     * 模板id
     */
    private String templateID;

    /**
     * 模板参数个数
     */
    private int templateParamCnt;

    /**
     * @see SmsProvider
     */
    private int smsProvider;

    /**
     * 模板参数
     */
    private Map<String, String> templateParams;

    /**
     * 手机号码
     */
    private List<String> phones;

}
