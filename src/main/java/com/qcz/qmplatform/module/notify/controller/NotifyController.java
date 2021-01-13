package com.qcz.qmplatform.module.notify.controller;

import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.utils.FileUtils;
import com.qcz.qmplatform.common.utils.SmsUtils;
import com.qcz.qmplatform.common.utils.SubjectUtils;
import com.qcz.qmplatform.module.notify.bean.SmsConfig;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/notify")
public class NotifyController {

    private static final String PREFIX = "/module/notify/";

    @Autowired
    private StringEncryptor encryptor;

    /**
     * 短信配置页面
     */
    @GetMapping("/smsConfigPage")
    public String smsConfigPage() {
        return PREFIX + "smsConfig";
    }

    @GetMapping("/getSmsConfig")
    @ResponseBody
    public ResponseResult<SmsConfig> getSmsConfig() {
        SmsConfig smsConfig = FileUtils.readObjectFromFile(SmsUtils.DAT_SMS_CONFIG, SmsConfig.class);
        smsConfig.setSecretKey(SubjectUtils.PASSWORD_UNCHANGED);
        return ResponseResult.ok(smsConfig);
    }

    @PostMapping("/saveSmsConfig")
    @ResponseBody
    public ResponseResult<?> saveSmsConfig(@RequestBody SmsConfig smsConfig) {
        String secretKey = smsConfig.getSecretKey();
        String encSecretKey;
        if (SubjectUtils.passwordChanged(secretKey)) {
            encSecretKey = encryptor.encrypt(secretKey);
        } else {
            encSecretKey = FileUtils.readObjectFromFile(SmsUtils.DAT_SMS_CONFIG, SmsConfig.class).getSecretKey();
        }
        smsConfig.setSecretKey(encSecretKey);
        FileUtils.writeObjectToFile(smsConfig, SmsUtils.DAT_SMS_CONFIG);
        return ResponseResult.ok();
    }

}
