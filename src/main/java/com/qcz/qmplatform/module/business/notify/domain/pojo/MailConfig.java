package com.qcz.qmplatform.module.business.notify.domain.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 邮件配置信息
 */
@Data
@Accessors(chain = true)
public class MailConfig implements Serializable {

    /**
     * 邮件服务地址
     */
    private String host;

    /**
     * 服务端口
     */
    private int port;

    /**
     * 是否需要加密连接
     */
    private boolean enableSSL = false;

    /**
     * 发送人地址
     */
    private String senderHost;

    /**
     * 发送人密码（部分邮箱是授权码）
     */
    private String senderPwd;

}
