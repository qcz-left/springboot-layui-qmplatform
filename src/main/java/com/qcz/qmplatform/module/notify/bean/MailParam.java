package com.qcz.qmplatform.module.notify.bean;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * 邮件内容信息
 */
public class MailParam implements Serializable {
    private static final long serialVersionUID = 4291630859477619874L;

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

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isHtml() {
        return html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}
