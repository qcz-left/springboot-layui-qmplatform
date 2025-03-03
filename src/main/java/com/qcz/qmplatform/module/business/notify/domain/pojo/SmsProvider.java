package com.qcz.qmplatform.module.business.notify.domain.pojo;

import cn.hutool.core.util.EnumUtil;
import com.qcz.qmplatform.module.business.notify.service.INotifyService;
import com.qcz.qmplatform.module.business.notify.service.ali.AliyunSmsNotifyService;
import com.qcz.qmplatform.module.business.notify.service.baidu.BaiduSmsNotifyService;
import com.qcz.qmplatform.module.business.notify.service.huawei.HuaweiSmsNotifyService;
import com.qcz.qmplatform.module.business.notify.service.tencent.TencentCloudSmsNotifyService;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 短信提供商
 */
@AllArgsConstructor
@Getter
public enum SmsProvider {

    /**
     * 腾讯
     */
    TENCENT(1, TencentCloudSmsNotifyService.class),

    /**
     * 阿里
     */
    ALI(2, AliyunSmsNotifyService.class),

    /**
     * 华为
     */
    HUAWEI(3, HuaweiSmsNotifyService.class),

    /**
     * 百度
     */
    BAIDU(4, BaiduSmsNotifyService.class);

    /**
     * 服务器标识码
     */
    private final int code;

    /**
     * 短信发送实现类
     */
    private final Class<? extends INotifyService> notifyServiceClass;

    /**
     * 根据标识码获取短信发送实现类
     *
     * @param code 服务商标识码
     */
    public static Class<? extends INotifyService> getNotifyServiceClass(int code) {
        return EnumUtil.getBy(SmsProvider::getCode, code).getNotifyServiceClass();
    }
}
