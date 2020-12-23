package com.qcz.qmplatform.module.notify;

import com.qcz.qmplatform.module.notify.bean.SmsConfig;
import com.qcz.qmplatform.module.notify.service.INotifyService;

public class NotifyServiceFactory {

    public static INotifyService build(Class<? extends INotifyService> clazz) {
        return build(clazz, null);
    }

    public static INotifyService build(Class<? extends INotifyService> clazz, SmsConfig smsConfig) {
        INotifyService notifyService = null;
        try {
            notifyService = clazz.newInstance();
            notifyService.setSmsConfig(smsConfig);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return notifyService;
    }

}
