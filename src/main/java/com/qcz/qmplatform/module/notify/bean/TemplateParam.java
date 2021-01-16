package com.qcz.qmplatform.module.notify.bean;

import java.io.Serializable;

public class TemplateParam implements Serializable {
    /**
     * 模板类型
     */
    private int templateType;

    /**
     * 模板ID
     */
    private String templateID;

    /**
     * 模板参数个数
     */
    private int paramCnt;

    public int getTemplateType() {
        return templateType;
    }

    public void setTemplateType(int templateType) {
        this.templateType = templateType;
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
