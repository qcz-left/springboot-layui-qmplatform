package com.qcz.qmplatform.module.business.notify.service.tencent.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class TencentCloudSmsSendStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    private String SerialNo;
    private String PhoneNumber;
    private int Fee;
    private String SessionContext;
    private String Code;
    private String Message;
    private String IsoCode;
}
