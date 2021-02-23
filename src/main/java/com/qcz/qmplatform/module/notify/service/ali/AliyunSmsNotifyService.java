package com.qcz.qmplatform.module.notify.service.ali;

import cn.hutool.json.JSONUtil;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.teaopenapi.models.Config;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.notify.bean.SmsConfig;
import com.qcz.qmplatform.module.notify.service.INotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 阿里云短信服务
 */
public class AliyunSmsNotifyService implements INotifyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AliyunSmsNotifyService.class);

    private SmsConfig smsConfig;

    @Override
    public String send() {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(smsConfig.getSecretId())
                // 您的AccessKey Secret
                .setAccessKeySecret(smsConfig.getSecretKey());
        // 访问的域名
        config.endpoint = StringUtils.blankToDefault(smsConfig.getEndpoint(), "dysmsapi.aliyuncs.com");
        Client client;
        try {
            client = new Client(config);
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(smsConfig.getPhones().get(0))
                    .setSignName(smsConfig.getSign())
                    .setTemplateCode(smsConfig.getTemplateID())
                    .setTemplateParam(JSONUtil.toJsonStr(smsConfig.getTemplateParams()));
            SendSmsResponse sendSmsResponse = client.sendSms(sendSmsRequest);
            SendSmsResponseBody body = sendSmsResponse.getBody();
            LOGGER.info(JSONUtil.toJsonStr(body));
            return body.getCode();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return null;
    }

    @Override
    public void setSmsConfig(SmsConfig smsConfig) {
        this.smsConfig = smsConfig;
    }
}
