package com.qcz.qmplatform.module.notify.controller;

import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.utils.FileUtils;
import com.qcz.qmplatform.common.utils.MailUtils;
import com.qcz.qmplatform.common.utils.SecureUtils;
import com.qcz.qmplatform.common.utils.SmsUtils;
import com.qcz.qmplatform.module.notify.bean.MailConfig;
import com.qcz.qmplatform.module.notify.bean.MailParam;
import com.qcz.qmplatform.module.notify.service.mail.MailNotifyService;
import com.qcz.qmplatform.module.notify.vo.SmsConfigVO;
import com.qcz.qmplatform.module.notify.vo.TestMailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/notify")
@Module("通讯配置")
public class NotifyController {

    private static final String PREFIX = "/module/notify/";

    @Autowired
    MailNotifyService mailNotifyService;

    /**
     * 短信配置页面
     */
    @GetMapping("/smsConfigPage")
    public String smsConfigPage() {
        return PREFIX + "smsConfig";
    }

    /**
     * 邮箱配置页面
     */
    @GetMapping("/mailConfigPage")
    public String mailConfigPage() {
        return PREFIX + "mailConfig";
    }

    @GetMapping("/getSmsConfig")
    @ResponseBody
    public ResponseResult<SmsConfigVO> getSmsConfig() {
        SmsConfigVO smsConfigVO = FileUtils.readObjectFromFile(SmsUtils.DAT_SMS_CONFIG, SmsConfigVO.class);
        smsConfigVO.setSecretKey(SecureUtils.PASSWORD_UNCHANGED);
        return ResponseResult.ok(smsConfigVO);
    }

    @GetMapping("/getMailConfig")
    @ResponseBody
    public ResponseResult<MailConfig> getMailConfig() {
        MailConfig mailConfig = FileUtils.readObjectFromFile(MailUtils.DAT_MAIL_CONFIG, MailConfig.class);
        mailConfig.setSenderPwd(SecureUtils.PASSWORD_UNCHANGED);
        return ResponseResult.ok(mailConfig);
    }

    @PostMapping("/saveSmsConfig")
    @ResponseBody
    @RecordLog(type = OperateType.UPDATE, description = "修改短信配置信息")
    public ResponseResult<?> saveSmsConfig(@RequestBody SmsConfigVO smsConfigVO) {
        String secretKey = smsConfigVO.getSecretKey();
        String encSecretKey;
        if (SecureUtils.passwordChanged(secretKey)) {
            encSecretKey = SecureUtils.aesEncrypt(secretKey);
        } else {
            encSecretKey = FileUtils.readObjectFromFile(SmsUtils.DAT_SMS_CONFIG, SmsConfigVO.class).getSecretKey();
        }
        smsConfigVO.setSecretKey(encSecretKey);
        FileUtils.writeObjectToFile(smsConfigVO, SmsUtils.DAT_SMS_CONFIG);
        return ResponseResult.ok();
    }

    @PostMapping("/saveMailConfig")
    @ResponseBody
    @RecordLog(type = OperateType.UPDATE, description = "修改邮箱配置信息")
    public ResponseResult<?> saveMailConfig(@RequestBody MailConfig mailConfig) {
        String senderPwd = mailConfig.getSenderPwd();
        String encSenderPwd;
        if (SecureUtils.passwordChanged(senderPwd)) {
            encSenderPwd = SecureUtils.rsaEncrypt(senderPwd);
        } else {
            encSenderPwd = FileUtils.readObjectFromFile(MailUtils.DAT_MAIL_CONFIG, MailConfig.class).getSenderPwd();
        }
        mailConfig.setSenderPwd(encSenderPwd);
        FileUtils.writeObjectToFile(mailConfig, MailUtils.DAT_MAIL_CONFIG);
        return ResponseResult.ok();
    }

    @PostMapping("/sendMail")
    @ResponseBody
    @RecordLog(type = OperateType.UPDATE, description = "发送邮件")
    public ResponseResult<?> sendMail(@RequestBody MailParam mailParam) {
        mailNotifyService.send(mailParam);
        return ResponseResult.ok();
    }

    @PostMapping("/testSendMail")
    @ResponseBody
    @RecordLog(type = OperateType.UPDATE, description = "测试发送邮件")
    public ResponseResult<?> testSendMail(@RequestBody TestMailVO testMailVO) {
        MailConfig mailConfig = testMailVO.getMailConfig();
        if (!SecureUtils.passwordChanged(mailConfig.getSenderPwd())) {
            String encSenderPwd = FileUtils.readObjectFromFile(MailUtils.DAT_MAIL_CONFIG, MailConfig.class).getSenderPwd();
            mailConfig.setSenderPwd(SecureUtils.rsaDecrypt(encSenderPwd));
        }
        mailNotifyService.send(mailConfig, testMailVO.getMailParam());
        return ResponseResult.ok();
    }

}
