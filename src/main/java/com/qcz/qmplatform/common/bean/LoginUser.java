package com.qcz.qmplatform.common.bean;

import com.qcz.qmplatform.module.business.system.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 登录用户信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class LoginUser extends User implements Serializable {

    /**
     * 客户端IP
     */
    private String clientIp;

}
