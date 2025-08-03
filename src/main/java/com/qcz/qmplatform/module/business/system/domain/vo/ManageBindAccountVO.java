package com.qcz.qmplatform.module.business.system.domain.vo;

import lombok.Data;

/**
 * 第三方绑定账号
 */
@Data
public class ManageBindAccountVO {

    /**
     * 第三方接入用户唯一id
     */
    private String thirdpartyId;

    /**
     * 平台名称
     */
    private String appName;

    /**
     * 账号名称
     */
    private String accountName;

}
