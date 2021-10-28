package com.qcz.qmplatform.module.notify.bean;

import java.io.Serializable;

/**
 * 邮件配置信息
 */
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
    private boolean enableSSL;

    /**
     * 发送人地址
     */
    private String senderHost;

    /**
     * 发送人密码（部分邮箱是授权码）
     */
    private String senderPwd;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isEnableSSL() {
        return enableSSL;
    }

    public void setEnableSSL(boolean enableSSL) {
        this.enableSSL = enableSSL;
    }

    public String getSenderHost() {
        return senderHost;
    }

    public void setSenderHost(String senderHost) {
        this.senderHost = senderHost;
    }

    public String getSenderPwd() {
        return senderPwd;
    }

    public void setSenderPwd(String senderPwd) {
        this.senderPwd = senderPwd;
    }
}
