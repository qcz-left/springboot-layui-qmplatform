package com.qcz.qmplatform.module.business.notify.service.tencent.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 腾讯云短信响应体
 */
@Data
public class TencentCloudSmsResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String RequestId;

    private TencentCloudSmsError Error;

    private List<TencentCloudSmsSendStatus> SendStatusSet;
}
