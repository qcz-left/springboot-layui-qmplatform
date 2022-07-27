package com.qcz.qmplatform.module.notify.service.tencent;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.qcz.qmplatform.module.notify.bean.SmsConfig;
import com.qcz.qmplatform.module.notify.service.INotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * 腾讯云短信服务
 */
public class TencentCloudSmsNotifyService implements INotifyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TencentCloudSmsNotifyService.class);

    private SmsConfig smsConfig;

    @Override
    public String send() {
        try {
            SmsSingleSender sender = new SmsSingleSender(Integer.parseInt(smsConfig.getAppId()), smsConfig.getAppKey());

            SmsSingleSenderResult result = sender.sendWithParam("86", smsConfig.getPhones().get(0), Integer.parseInt(smsConfig.getTemplateID()), new ArrayList<>(smsConfig.getTemplateParams().values()), smsConfig.getSign(), null, null);
            int code = result.result;
            LOGGER.info("result code: {}, result message: {}", code, result.errMsg);
            return code == 0 ? "ok" : String.valueOf(code);
        } catch (Exception e) {
            LOGGER.error("", e);
            return e.getMessage();
        }
    }

    @Override
    public void setSmsConfig(SmsConfig smsConfig) {
        this.smsConfig = smsConfig;
    }
}
