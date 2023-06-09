package com.qcz.qmplatform.module.notify.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
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

}
