package com.qcz.qmplatform.module.notify.vo;

import com.qcz.qmplatform.module.notify.bean.MailConfig;
import com.qcz.qmplatform.module.notify.bean.MailParam;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class TestMailVO implements Serializable {

    private MailConfig mailConfig;

    private MailParam mailParam;

}
