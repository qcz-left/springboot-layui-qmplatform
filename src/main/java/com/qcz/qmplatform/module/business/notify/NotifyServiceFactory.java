package com.qcz.qmplatform.module.business.notify;

import com.qcz.qmplatform.common.exception.BusinessException;
import com.qcz.qmplatform.common.utils.SmsUtils;
import com.qcz.qmplatform.module.business.notify.domain.pojo.SmsConfig;
import com.qcz.qmplatform.module.business.notify.domain.pojo.SmsProvider;
import com.qcz.qmplatform.module.business.notify.service.INotifyService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotifyServiceFactory {

    public static INotifyService build(Class<? extends INotifyService> clazz, SmsConfig smsConfig) {
        INotifyService notifyService = null;
        try {
            int smsProvider = smsConfig.getSmsProvider();
            Class<? extends INotifyService> notifyServiceClass;
            if (clazz == null) {
                if (smsProvider <= 0) {
                    throw new BusinessException("必须指定一个短信提供商！");
                }
                notifyServiceClass = SmsUtils.getNotifyServiceClass(SmsProvider.valueOf(smsProvider));
            } else {
                notifyServiceClass = clazz;
            }
            notifyService = notifyServiceClass.newInstance();
            notifyService.setSmsConfig(smsConfig);
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("build notify service impl failed!", e);
        }
        return notifyService;
    }

    public static INotifyService build(SmsConfig smsConfig) {
        return build(null, smsConfig);
    }

}
