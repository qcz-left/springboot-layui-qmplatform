package com.qcz.qmplatform.module.system.vo;

import com.qcz.qmplatform.module.system.domain.Message;

public class MessageVO extends Message {

    /**
     * 类型名称（字典）
     */
    private String typeName;

    /**
     * 发送人名称
     */
    private String senderName;

    /**
     * 接收人名称
     */
    private String receiverName;

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
