package com.qcz.qmplatform.module.notify.domain.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * 邮件内容信息
 */
@Data
@Accessors(chain = true)
public class MailParam implements Serializable {

    /**
     * 接收人地址
     */
    private String to;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 是否HTML内容
     */
    private boolean html;

    /**
     * 附件列表
     */
    private List<File> files;

}
