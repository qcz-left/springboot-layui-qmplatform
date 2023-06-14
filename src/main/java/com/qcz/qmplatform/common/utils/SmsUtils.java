package com.qcz.qmplatform.common.utils;

import com.qcz.qmplatform.module.notify.bean.SmsProvider;
import com.qcz.qmplatform.module.notify.service.INotifyService;
import com.qcz.qmplatform.module.notify.service.ali.AliyunSmsNotifyService;
import com.qcz.qmplatform.module.notify.service.baidu.BaiduSmsNotifyService;
import com.qcz.qmplatform.module.notify.service.huawei.HuaweiSmsNotifyService;
import com.qcz.qmplatform.module.notify.service.tencent.TencentCloudSmsNotifyService;

/**
 * 短信工具类
 */
public class SmsUtils {

    public static final String DAT_SMS_CONFIG = FileUtils.WEB_PATH + "/Dat/smsConfig.dat";

    public static Class<? extends INotifyService> getNotifyServiceClass(SmsProvider smsProvider) {
        Class<? extends INotifyService> clazz = null;
        if (smsProvider == SmsProvider.TENCENT) {
            clazz = TencentCloudSmsNotifyService.class;
        } else if (smsProvider == SmsProvider.ALI) {
            clazz = AliyunSmsNotifyService.class;
        } else if (smsProvider == SmsProvider.HUAWEI) {
            clazz = HuaweiSmsNotifyService.class;
        } else if (smsProvider == SmsProvider.BAIDU) {
            clazz = BaiduSmsNotifyService.class;
        }

        return clazz;
    }

}
