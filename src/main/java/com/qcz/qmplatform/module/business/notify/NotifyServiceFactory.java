package com.qcz.qmplatform.module.business.notify;

import cn.hutool.core.util.ReflectUtil;
import com.qcz.qmplatform.module.business.notify.domain.pojo.SmsConfig;
import com.qcz.qmplatform.module.business.notify.domain.pojo.SmsProvider;
import com.qcz.qmplatform.module.business.notify.service.INotifyService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotifyServiceFactory {

    public static INotifyService createNotifyService(SmsConfig smsConfig) {
        int smsProvider = smsConfig.getSmsProvider();
        INotifyService notifyService = ReflectUtil.newInstance(SmsProvider.getNotifyServiceClass(smsProvider));
        notifyService.setSmsConfig(smsConfig);
        return notifyService;
    }

}
