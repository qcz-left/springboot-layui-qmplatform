package com.qcz.qmplatform.module.business.system.domain;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.qcz.qmplatform.common.bean.DBProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统消息
 * </p>
 *
 * @author quchangzhong
 * @since 2021-03-26
 */
@Data
@Accessors(chain = true)
@TableName(DBProperties.Table.SYS_MESSAGE)
public class Message implements Serializable {

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

    @TableField("instance")
    private String instance;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 上次更新时间
     */
    @TableField("last_update_time")
    private LocalDateTime lastUpdateTime;

    /**
     * 是否已读
     */
    @TableField("readed")
    private Integer readed;

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

}
