package com.qcz.qmplatform.module.notify.bean;

import java.io.Serializable;

public class TemplateParam implements Serializable {
    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 模板名称
     */
    private String templateID;

    /**
     * 模板参数个数
     */
    private int paramCnt;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateID() {
        return templateID;
    }

    public void setTemplateID(String templateID) {
        this.templateID = templateID;
    }

    public int getParamCnt() {
        return paramCnt;
    }

    public void setParamCnt(int paramCnt) {
        this.paramCnt = paramCnt;
    }
}
