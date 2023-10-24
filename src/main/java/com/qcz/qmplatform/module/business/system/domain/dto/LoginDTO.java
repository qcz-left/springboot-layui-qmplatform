package com.qcz.qmplatform.module.business.system.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class LoginDTO extends PasswordDTO {

    /**
     * 第三方系统
     */
    private String thirdparty;
    /**
     * 第三方系统账号id
     */
    private String thirdparyId;

}
