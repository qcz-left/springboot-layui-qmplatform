package com.qcz.qmplatform.module.notify.vo;

import com.qcz.qmplatform.module.notify.bean.SmsProvider;
import com.qcz.qmplatform.module.notify.bean.TemplateParam;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

@Data
@Accessors(chain = true)
public class SmsConfigVO implements Serializable {

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
     * 通道号（华为云使用）
     */
    private String channelNumber;

    /**
     * @see SmsProvider
     */
    private int smsProvider;

    /**
     * 模板参数列表
     */
    private Map<Integer, TemplateParam> templateParams;

}
