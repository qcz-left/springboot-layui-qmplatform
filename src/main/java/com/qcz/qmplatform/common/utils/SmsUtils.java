package com.qcz.qmplatform.common.utils;

import com.qcz.qmplatform.module.notify.bean.SmsProvider;
import com.qcz.qmplatform.module.notify.service.INotifyService;
import com.qcz.qmplatform.module.notify.service.ali.AliyunSmsNotifyService;
import com.qcz.qmplatform.module.notify.service.tencent.TencentCloudSmsNotifyService;

public class SmsUtils {

    public static Class<? extends INotifyService> getNotifyServiceClass(int smsProviderCode) {
        Class<? extends INotifyService> clazz = null;
        if (smsProviderCode == SmsProvider.TENCENT.code()) {
            clazz = TencentCloudSmsNotifyService.class;
        } else if (smsProviderCode == SmsProvider.ALI.code()) {
            clazz = AliyunSmsNotifyService.class;
        }

        return clazz;
    }
}
