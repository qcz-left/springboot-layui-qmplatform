package com.qcz.qmplatform.module.notify.vo;

import com.qcz.qmplatform.module.notify.bean.MailConfig;
import com.qcz.qmplatform.module.notify.bean.MailParam;

import java.io.Serializable;

public class TestMailVO implements Serializable {
    private static final long serialVersionUID = 5936568706567207595L;

    private MailConfig mailConfig;

    private MailParam mailParam;

    public MailConfig getMailConfig() {
        return mailConfig;
    }

    public void setMailConfig(MailConfig mailConfig) {
        this.mailConfig = mailConfig;
    }

    public MailParam getMailParam() {
        return mailParam;
    }

    public void setMailParam(MailParam mailParam) {
        this.mailParam = mailParam;
    }
}
