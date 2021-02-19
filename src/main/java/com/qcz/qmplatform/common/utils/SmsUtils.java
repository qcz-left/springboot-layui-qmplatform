package com.qcz.qmplatform.common.utils;

import com.qcz.qmplatform.module.notify.bean.SmsProvider;
import com.qcz.qmplatform.module.notify.service.INotifyService;
import com.qcz.qmplatform.module.notify.service.ali.AliyunSmsNotifyService;
import com.qcz.qmplatform.module.notify.service.tencent.TencentCloudSmsNotifyService;

public class SmsUtils {

    public static final String DAT_SMS_CONFIG = FileUtils.WEB_PATH + "/Dat/smsConfig.dat";

    public static Class<? extends INotifyService> getNotifyServiceClass(int smsProviderCode) {
        Class<? extends INotifyService> clazz = null;
        SmsProvider smsProvider = SmsProvider.valueOf(smsProviderCode);
        if (smsProvider == SmsProvider.TENCENT) {
            clazz = TencentCloudSmsNotifyService.class;
        } else if (smsProvider == SmsProvider.ALI) {
            clazz = AliyunSmsNotifyService.class;
        }

        return clazz;
    }

}
