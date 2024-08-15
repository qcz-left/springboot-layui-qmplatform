package com.qcz.qmplatform.module.business.notify.domain.vo;

import com.qcz.qmplatform.module.business.notify.domain.pojo.MailConfig;
import com.qcz.qmplatform.module.business.notify.domain.pojo.MailParam;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class TestMailVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private MailConfig mailConfig;

    private MailParam mailParam;

}
