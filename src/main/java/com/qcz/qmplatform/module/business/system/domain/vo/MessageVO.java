package com.qcz.qmplatform.module.business.system.domain.vo;

import com.qcz.qmplatform.module.business.system.domain.Message;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
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

}
