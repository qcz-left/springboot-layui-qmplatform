package com.qcz.qmplatform.module.notify.service.mail;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.extra.mail.MailAccount;
import com.qcz.qmplatform.common.exception.CommonException;
import com.qcz.qmplatform.common.utils.FileUtils;
import com.qcz.qmplatform.common.utils.MailUtils;
import com.qcz.qmplatform.common.utils.SecureUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.module.notify.bean.MailConfig;
import com.qcz.qmplatform.module.notify.bean.MailParam;
import com.sun.mail.util.MailSSLSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.security.GeneralSecurityException;
import java.util.List;

/**
 * 邮件通知服务
 */
@Service
public class MailNotifyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailNotifyService.class);

    /**
     * 发送邮件，使用已保存的配置
     *
     * @param mailParam 邮件内容
     */
    public void send(MailParam mailParam) {
        MailConfig mailConfig = FileUtils.readObjectFromFile(MailUtils.DAT_MAIL_CONFIG, MailConfig.class);
        mailConfig.setSenderPwd(SecureUtils.rsaDecrypt(mailConfig.getSenderPwd()));
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
            throw new CommonException("未读取到邮件配置信息");
        }

        MailAccount mailAccount = new MailAccount();
        mailAccount.setHost(mailConfig.getHost());
        mailAccount.setPort(mailConfig.getPort());
        mailAccount.setSslEnable(mailConfig.isEnableSSL());
        mailAccount.setFrom(mailConfig.getSenderHost());
        mailAccount.setPass(mailConfig.getSenderPwd());
        mailAccount.setAuth(true);
        if (mailConfig.isEnableSSL()) {
            try {
                MailSSLSocketFactory sf = new MailSSLSocketFactory();
                sf.setTrustAllHosts(true);
                mailAccount.setSocketFactoryClass(sf.getClass().getName());
                mailAccount.setSocketFactoryPort(mailConfig.getPort());
            } catch (GeneralSecurityException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        List<File> files = mailParam.getFiles();

        if (files == null) {
            MailUtils.send(mailAccount, mailParam.getTo(), mailParam.getSubject(), mailParam.getContent(), mailParam.isHtml());
        } else {
            MailUtils.send(mailAccount, mailParam.getTo(), mailParam.getSubject(), mailParam.getContent(), mailParam.isHtml(), ArrayUtil.toArray(files, File.class));
        }
    }

}
