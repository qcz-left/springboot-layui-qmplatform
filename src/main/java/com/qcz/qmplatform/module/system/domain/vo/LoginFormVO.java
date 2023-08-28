package com.qcz.qmplatform.module.system.domain.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class LoginFormVO extends PasswordVO {

    /**
     * 第三方系统
     */
    private String thirdparty;
    /**
     * 第三方系统账号id
     */
    private String thirdparyId;

}
