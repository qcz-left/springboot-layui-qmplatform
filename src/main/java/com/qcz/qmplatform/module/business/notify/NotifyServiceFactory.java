package com.qcz.qmplatform.module.business.notify;

import cn.hutool.core.util.ReflectUtil;
import com.qcz.qmplatform.common.exception.BusinessException;
import com.qcz.qmplatform.common.utils.SmsUtils;
import com.qcz.qmplatform.module.business.notify.domain.pojo.SmsConfig;
import com.qcz.qmplatform.module.business.notify.domain.pojo.SmsProvider;
import com.qcz.qmplatform.module.business.notify.service.INotifyService;

public class NotifyServiceFactory {

    public static INotifyService build(Class<? extends INotifyService> clazz, SmsConfig smsConfig) {
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
        INotifyService notifyService = ReflectUtil.newInstance(notifyServiceClass);
        notifyService.setSmsConfig(smsConfig);
        return notifyService;
    }

    public static INotifyService build(SmsConfig smsConfig) {
        return build(null, smsConfig);
    }

}
