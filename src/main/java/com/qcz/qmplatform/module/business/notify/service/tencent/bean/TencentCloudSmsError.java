package com.qcz.qmplatform.module.business.notify.service.tencent.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class TencentCloudSmsError implements Serializable {

    private static final long serialVersionUID = 1L;

    private String Code;

    private String Message;

}
