package com.qcz.qmplatform.module.notify.service.mail;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.extra.mail.MailAccount;
import com.qcz.qmplatform.common.exception.BusinessException;
import com.qcz.qmplatform.common.utils.FileUtils;
import com.qcz.qmplatform.common.utils.MailUtils;
import com.qcz.qmplatform.common.utils.SecureUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.notify.domain.pojo.MailConfig;
import com.qcz.qmplatform.module.notify.domain.pojo.MailParam;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * 邮件通知服务
 */
@Service
public class MailNotifyService {

    /**
     * 发送邮件，使用已保存的配置
     *
     * @param mailParam 邮件内容
     */
    public void send(MailParam mailParam) {
        MailConfig mailConfig = FileUtils.readObjectFromFile(MailUtils.DAT_MAIL_CONFIG, MailConfig.class);
        mailConfig.setSenderPwd(SecureUtils.aesDecrypt(mailConfig.getSenderPwd()));
        send(mailConfig, mailParam);
    }

    /**
     * 发送
     *
     * @param mailConfig 邮箱配置信息
     * @param mailParam  邮件内容
     */
    public void send(MailConfig mailConfig, MailParam mailParam) {
        if (StringUtils.isBlank(mailConfig.getHost())) {
            throw new BusinessException("未读取到邮件配置信息");
        }

        MailAccount mailAccount = new MailAccount();
        mailAccount.setHost(mailConfig.getHost());
        mailAccount.setPort(mailConfig.getPort());
        mailAccount.setSslEnable(mailConfig.isEnableSSL());
        mailAccount.setFrom(mailConfig.getSenderHost());
        mailAccount.setPass(mailConfig.getSenderPwd());
        mailAccount.setAuth(true);

        List<File> files = mailParam.getFiles();

        if (files == null) {
            MailUtils.send(mailAccount, mailParam.getTo(), mailParam.getSubject(), mailParam.getContent(), mailParam.isHtml());
        } else {
            MailUtils.send(mailAccount, mailParam.getTo(), mailParam.getSubject(), mailParam.getContent(), mailParam.isHtml(), ArrayUtil.toArray(files, File.class));
        }
    }

}
