package com.qcz.qmplatform.module.notify.service.mail;

import com.qcz.qmplatform.module.notify.bean.MailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 邮件通知服务
 */
@Service
public class MailNotifyService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void send(MailConfig mailConfig) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailConfig.getFrom());
        mailMessage.setTo(mailConfig.getTo());
        mailMessage.setText(mailConfig.getContent());
        mailMessage.setSubject(mailConfig.getSubject());
        javaMailSender.send(mailMessage);
    }

}
