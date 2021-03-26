package com.qcz.qmplatform.module.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * <p>
 * 系统消息
 * </p>
 *
 * @author quchangzhong
 * @since 2021-03-26
 */
@TableName("sys_message")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系统通知信息id
     */
    @TableId("message_id")
    private String messageId;

    /**
     * 消息内容
     */
    @TableField("content")
    private String content;

    /**
     * 消息类型
     */
    @TableField("type")
    private Integer type;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 是否已读
     */
    @TableField("read")
    private Integer read;

    /**
     * 发送方
     */
    @TableField("sender")
    private String sender;

    /**
     * 接收方
     */
    @TableField("receiver")
    private String receiver;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getRead() {
        return read;
    }

    public void setRead(Integer read) {
        this.read = read;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", content=" + content +
                ", type=" + type +
                ", createTime=" + createTime +
                ", read=" + read +
                ", sender=" + sender +
                ", receiver=" + receiver +
                "}";
    }
}
