package com.qcz.qmplatform.module.business.notify.controller;

import com.qcz.qmplatform.common.aop.annotation.Module;
import com.qcz.qmplatform.common.aop.annotation.RecordLog;
import com.qcz.qmplatform.common.aop.assist.OperateType;
import com.qcz.qmplatform.common.bean.PrivCode;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.utils.FileUtils;
import com.qcz.qmplatform.common.utils.MailUtils;
import com.qcz.qmplatform.common.utils.SecureUtils;
import com.qcz.qmplatform.common.utils.SmsUtils;
import com.qcz.qmplatform.module.business.notify.domain.pojo.MailConfig;
import com.qcz.qmplatform.module.business.notify.domain.pojo.MailParam;
import com.qcz.qmplatform.module.business.notify.service.mail.MailNotifyService;
import com.qcz.qmplatform.module.business.notify.domain.vo.SmsConfigVO;
import com.qcz.qmplatform.module.business.notify.domain.vo.TestMailVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.annotation.Resource;

@Controller
@RequestMapping("/notify")
@Module("通讯配置")
public class NotifyController {

    @Resource
    MailNotifyService mailNotifyService;

    /**
     * 短信配置页面
     */
    @GetMapping("/smsConfigPage")
    public String smsConfigPage() {
        return "/module/notify/smsConfig";
    }

    /**
     * 邮箱配置页面
     */
    @GetMapping("/mailConfigPage")
    public String mailConfigPage() {
        return "/module/notify/mailConfig";
    }

    @GetMapping("/getSmsConfig")
    @ResponseBody
    public ResponseResult<SmsConfigVO> getSmsConfig() {
        SmsConfigVO smsConfigVO = FileUtils.readObjectFromFile(SmsUtils.DAT_SMS_CONFIG, SmsConfigVO.class);
        smsConfigVO.setSecretKey(SecureUtils.PASSWORD_UNCHANGED);
        smsConfigVO.setAppKey(SecureUtils.PASSWORD_UNCHANGED);
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
    public ResponseResult<Void> saveSmsConfig(@RequestBody SmsConfigVO smsConfigVO) {
        String secretKey = smsConfigVO.getSecretKey();
        String encSecretKey;
        if (SecureUtils.passwordChanged(secretKey)) {
            encSecretKey = SecureUtils.aesEncrypt(secretKey);
        } else {
            encSecretKey = FileUtils.readObjectFromFile(SmsUtils.DAT_SMS_CONFIG, SmsConfigVO.class).getSecretKey();
        }
        String appKey = smsConfigVO.getAppKey();
        String encAppKey;
        if (SecureUtils.passwordChanged(appKey)) {
            encAppKey = SecureUtils.aesEncrypt(appKey);
        } else {
            encAppKey = FileUtils.readObjectFromFile(SmsUtils.DAT_SMS_CONFIG, SmsConfigVO.class).getAppKey();
        }
        smsConfigVO.setSecretKey(encSecretKey);
        smsConfigVO.setAppKey(encAppKey);
        FileUtils.writeObjectToFile(smsConfigVO, SmsUtils.DAT_SMS_CONFIG);
        return ResponseResult.ok();
    }

    @PostMapping("/saveMailConfig")
    @ResponseBody
    @RecordLog(type = OperateType.UPDATE, description = "修改邮箱配置信息")
    @RequiresPermissions(PrivCode.BTN_CODE_MAIL_CONFIG_SAVE)
    public ResponseResult<Void> saveMailConfig(@RequestBody MailConfig mailConfig) {
        String senderPwd = mailConfig.getSenderPwd();
        String encSenderPwd;
        if (SecureUtils.passwordChanged(senderPwd)) {
            encSenderPwd = SecureUtils.aesEncrypt(senderPwd);
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
    public ResponseResult<Void> sendMail(@RequestBody MailParam mailParam) {
        mailNotifyService.send(mailParam);
        return ResponseResult.ok();
    }

    @PostMapping("/testSendMail")
    @ResponseBody
    @RecordLog(type = OperateType.UPDATE, description = "测试发送邮件")
    public ResponseResult<Void> testSendMail(@RequestBody TestMailVO testMailVO) {
        MailConfig mailConfig = testMailVO.getMailConfig();
        if (!SecureUtils.passwordChanged(mailConfig.getSenderPwd())) {
            String encSenderPwd = FileUtils.readObjectFromFile(MailUtils.DAT_MAIL_CONFIG, MailConfig.class).getSenderPwd();
            mailConfig.setSenderPwd(SecureUtils.aesDecrypt(encSenderPwd));
        }
        mailNotifyService.send(mailConfig, testMailVO.getMailParam());
        return ResponseResult.ok();
    }

}
